package com.antonio.proyecto.dialogues

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DeleteDialog {
    fun showConfirmationDialog(
        context : Context,
        onConfirm: () -> Unit,
        onCancel: () -> Unit = {}
        ){

        val dialog = AlertDialog.Builder(context)
            .setTitle("Confirmación")
            .setMessage("¿Seguro que quieres borrar la receta?")
            .setPositiveButton("si"){_, _ ->
                onConfirm()
            }
            .setNegativeButton("no"){dialog, _ ->
                onCancel()
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}