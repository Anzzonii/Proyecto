package com.antonio.proyecto.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.antonio.proyecto.R
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.models.Receta
import com.antonio.proyecto.object_models.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AddDialog {

    fun showAddDialog(
        context: Context,
        onConfirm:(Receta) -> Unit
    ) {



                val builder = AlertDialog.Builder(context)
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.dialog_add_receta, null)


                val addNombre = view.findViewById<EditText>(R.id.add_name)
                val addAutor = view.findViewById<EditText>(R.id.add_autor)
                val addLevel = view.findViewById<EditText>(R.id.add_level)
                val addImage = view.findViewById<EditText>(R.id.add_image)
                val addIngredientes = view.findViewById<EditText>(R.id.add_ingredientes)
                val addElaboracion = view.findViewById<EditText>(R.id.add_elaboracion)




                builder.setView(view)
                    .setTitle("Añadir receta")
                    .setPositiveButton("Guardar") { _, _ ->
                        val nuevaReceta = Receta(
                            id = "",
                            name = addNombre.text.toString(),
                            autor = addAutor.text.toString(),
                            level = addLevel.text.toString(),
                            image = addImage.text.toString(),
                            correo = FirebaseAuth.getInstance().currentUser?.email.toString(),
                            ingredientes = addIngredientes.text.toString(),
                            elaboracion = addElaboracion.text.toString()
                        )
                        onConfirm(nuevaReceta)
                    }
                    .setNegativeButton("Cancelar", null)

                val dialog = builder.create()
                dialog.show()

            }
    }

