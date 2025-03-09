package com.antonio.proyecto.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.antonio.proyecto.R
import com.antonio.proyecto.models.Receta
import com.google.firebase.auth.FirebaseAuth

class EditDialog {

    fun showEditDialog(
        context: Context,
        receta: Receta,
        onConfirm:(Receta) -> Unit
    ){

        //Crea el view del dialog
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_receta, null)

        //Guarda los EditText en los que vamos a modificar la receta
        val nombreEditText = view.findViewById<EditText>(R.id.edit_name)
        val autorEditText = view.findViewById<EditText>(R.id.edit_autor)
        val levelEditText = view.findViewById<EditText>(R.id.edit_level)
        val imageEditText = view.findViewById<EditText>(R.id.edit_image)
        val ingredientesEditText = view.findViewById<EditText>(R.id.edit_ingredientes)
        val elaboracionEditText = view.findViewById<EditText>(R.id.edit_elaboracion)

        //Le añadimos los anteriores valores que tenia la receta
        nombreEditText.setText(receta.name)
        autorEditText.setText(receta.autor)
        levelEditText.setText(receta.level)
        imageEditText.setText(receta.image)
        ingredientesEditText.setText(receta.ingredientes)
        elaboracionEditText.setText(receta.elaboracion)

        //Si confirma crea una nueva receta con los nuevos valores y la id de la que estamos editando y la actualiza
        builder.setView(view)
            .setTitle("Editar receta")
            .setPositiveButton("Guardar"){_, _ ->
                val updateReceta = Receta(receta.id, nombreEditText.text.toString(), autorEditText.text.toString(), levelEditText.text.toString(), imageEditText.text.toString(), ingredientesEditText.text.toString(), elaboracionEditText.text.toString())
                updateReceta.correo = FirebaseAuth.getInstance().currentUser?.email.toString()
                onConfirm(updateReceta)
            }
            .setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()

    }
}