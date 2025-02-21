package com.antonio.proyecto.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.antonio.proyecto.R
import com.antonio.proyecto.models.Receta

class EditDialog {

    fun showEditDialog(
        context: Context,
        receta: Receta,
        onConfirm:(Receta) -> Unit
    ){

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_receta, null)

        val nombreEditText = view.findViewById<EditText>(R.id.edit_name)
        val autorEditText = view.findViewById<EditText>(R.id.edit_autor)
        val levelEditText = view.findViewById<EditText>(R.id.edit_level)
        val imageEditText = view.findViewById<EditText>(R.id.edit_image)

        nombreEditText.setText(receta.name)
        autorEditText.setText(receta.autor)
        levelEditText.setText(receta.level)
        imageEditText.setText(receta.image)

        builder.setView(view)
            .setTitle("Editar receta")
            .setPositiveButton("Guardar"){_, _ ->
                val updateReceta = Receta(nombreEditText.text.toString(), autorEditText.text.toString(), levelEditText.text.toString(), imageEditText.text.toString())
                onConfirm(updateReceta)
            }
            .setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()

    }
}