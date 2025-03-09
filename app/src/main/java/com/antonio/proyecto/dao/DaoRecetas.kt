package com.antonio.proyecto.dao

import com.antonio.proyecto.interfaces.InterfaceDAO
import com.antonio.proyecto.models.Receta
import com.antonio.proyecto.object_models.Repository

//Clase que implementa la interfaz DAO para realizar las acciones que se van a usar en las recetas
class DaoRecetas private constructor():InterfaceDAO {

    //Objeto companion crea y mantiene una única instancia de DaoRecetas
    companion object {
        val myDao: DaoRecetas by lazy{
            DaoRecetas()
        }
    }

    //Función para obtener todas las recetas desde el respositorio
    override fun getDataReceta(callback: (List<Receta>) -> Unit) {
        Repository.getRecetas { recetas ->
            // Devuelve las recetas a través del callback
            callback(recetas)
        }
    }

    //Función para borrar una receta del repositorio
    override suspend fun removeReceta(receta : Receta) {
        Repository.deleteReceta(receta.id)
    }

    //Función para editar una función en el respositorio
    override suspend fun editReceta(recetaModificada: Receta) {
        Repository.updateReceta(recetaModificada)
    }

    //Función para añadir una receta al repostitorio
    override fun addReceta(receta: Receta, recetas: MutableList<Receta>) {
        recetas.add(receta)
    }


}