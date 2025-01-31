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


    override fun getDataReceta(): List<Receta> = Repository.listReceta

    override fun removeReceta(receta : Receta) {
        Repository.listReceta.remove(receta)
    }

    override fun editReceta(recetaModificada: Receta, recetas:MutableList<Receta>, position:Int) {
        recetas[position] = recetaModificada
    }

    override fun addReceta(receta: Receta, recetas: MutableList<Receta>) {
        recetas.add(receta)
    }


}