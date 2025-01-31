package com.antonio.proyecto.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.antonio.proyecto.R
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.models.Receta

class AddDialog {

    fun showAddDialog(
        context: Context,
        onConfirm:(Receta) -> Unit
    ){

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_receta, null)


        val addNombre = view.findViewById<EditText>(R.id.add_name)
        val addAutor = view.findViewById<EditText>(R.id.add_autor)
        val addLevel = view.findViewById<EditText>(R.id.add_level)
        val addImage = view.findViewById<EditText>(R.id.add_image)


        builder.setView(view)
            .setTitle("Editar receta")
            .setPositiveButton("Guardar"){_, _ ->
                val nuevaReceta = Receta(addNombre.text.toString(), addAutor.text.toString(), addLevel.text.toString(), addImage.text.toString())
                onConfirm(nuevaReceta)
            }
            .setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()

    }
}