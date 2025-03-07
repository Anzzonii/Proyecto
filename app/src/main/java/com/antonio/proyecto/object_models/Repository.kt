package com.antonio.proyecto.object_models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.antonio.proyecto.models.Receta
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await

object Repository {

    private val db = FirebaseFirestore.getInstance()
    private const val listaRecetas = "recetas"

    // Obtener todas las recetas con actualización en tiempo real
    fun getRecetas(callback: (List<Receta>) -> Unit) {
        db.collection(listaRecetas)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("Repository", "Error obteniendo los datos", exception)
                    callback(emptyList())  // Si hay error, devolvemos una lista vacía
                    return@addSnapshotListener
                }

                // Convertir los documentos a objetos Receta
                val recetas = snapshot?.documents?.mapNotNull { it.toObject(Receta::class.java) } ?: emptyList()
                callback(recetas)  // Devuelve la lista actualizada de recetas
            }
    }

    // Agregar una nueva receta
    suspend fun addReceta(receta: Receta): Boolean {
        return try {
            // Coge la colección de recetas y le añade la receta a añadir
            val documentRef = db.collection(listaRecetas).add(receta).await()

            // Actualiza el id de la receta con el id del documento
            documentRef.update("id", documentRef.id).await()
            true
        } catch (e: Exception) {
            Log.e("Repository", "Error añadiendo la receta", e)
            false
        }
    }

    // Actualizar una receta
    suspend fun updateReceta(receta: Receta): Boolean {
        return try {
            // Coge la colección de recetas y actualiza el documento con el id que le pasamos
            db.collection(listaRecetas).document(receta.id).set(receta).await()
            true
        } catch (e: Exception) {
            Log.e("Repository", "Error actualizando receta", e)
            false
        }
    }

    // Eliminar una receta
    suspend fun deleteReceta(id: String): Boolean {
        return try {
            if (id.isNotBlank()) {
                // Eliminar la receta por id
                db.collection(listaRecetas).document(id).delete().await()
                true
            } else {
                Log.e("Repository", "Error eliminando la receta (id vacío)")
                false
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error eliminando la receta", e)
            false
        }
    }
}
