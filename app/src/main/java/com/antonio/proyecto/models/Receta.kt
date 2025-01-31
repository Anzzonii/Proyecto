package com.antonio.proyecto.models

class Receta (

    var name : String,
    var autor : String,
    var level : String,
    var image : String

) {

    override fun toString(): String {

        return "Receta (name='$name', autor='$autor', level='$level', image='$image')"

    }

    

}

