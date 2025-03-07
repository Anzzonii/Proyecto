package com.antonio.proyecto.models

import java.io.Serializable

class Receta(
    var id: String = "",
    var name: String = "",
    var autor: String = "",
    var level: String = "",
    var image: String = "",
    var ingredientes: String = "",
    var elaboracion: String = "",
    var correo: String = ""
) : Serializable {
    // Constructor vacío requerido por Firebase
    constructor() : this("", "", "", "", "", "", "", "")


    override fun toString(): String {
        return "Receta (id=$id, name='$name', autor='$autor', level='$level', image='$image', ingredientes='$ingredientes', elaboracion='$elaboracion', correo='$correo')"
    }
}


