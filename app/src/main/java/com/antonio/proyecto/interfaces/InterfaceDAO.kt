package com.antonio.proyecto.interfaces

import com.antonio.proyecto.models.Receta

interface InterfaceDAO {

    fun getDataReceta(): List<Receta>

    fun removeReceta(receta : Receta)

    fun editReceta(recetaModificada: Receta, recetas: MutableList<Receta> ,position:Int)

    fun addReceta(receta: Receta, recetas:MutableList<Receta>)

}