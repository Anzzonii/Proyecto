package com.antonio.proyecto.interfaces

import com.antonio.proyecto.models.Receta

interface InterfaceDAO {

    fun getDataReceta(callback: (List<Receta>) -> Unit)

    suspend fun removeReceta(receta : Receta)

    suspend fun editReceta(recetaModificada: Receta)

    fun addReceta(receta: Receta, recetas:MutableList<Receta>)

}