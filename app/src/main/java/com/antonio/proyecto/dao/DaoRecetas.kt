package com.antonio.proyecto.dao

import com.antonio.proyecto.interfaces.InterfaceDAO
import com.antonio.proyecto.models.Receta
import com.antonio.proyecto.object_models.Repository

class DaoRecetas private constructor():InterfaceDAO {

    companion object {
        val myDao: DaoRecetas by lazy{
            DaoRecetas()
        }
    }


    override fun getDataReceta(callback: (List<Receta>) -> Unit) {
        Repository.getRecetas { recetas ->
            // Devuelve las recetas a través del callback
            callback(recetas)
        }
    }

    override suspend fun removeReceta(receta : Receta) {
        Repository.deleteReceta(receta.id)
    }

    override suspend fun editReceta(recetaModificada: Receta) {
        Repository.updateReceta(recetaModificada)
    }

    override fun addReceta(receta: Receta, recetas: MutableList<Receta>) {
        recetas.add(receta)
    }


}