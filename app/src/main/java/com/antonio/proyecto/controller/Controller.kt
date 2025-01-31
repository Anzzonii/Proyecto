package com.antonio.proyecto.controller

import android.content.Context
import android.widget.Toast
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.dao.DaoRecetas
import com.antonio.proyecto.dialogues.AddDialog
import com.antonio.proyecto.dialogues.DeleteDialog
import com.antonio.proyecto.dialogues.EditDialog
import com.antonio.proyecto.models.Receta

class Controller (val context : Context){
    lateinit var listRecetas : MutableList<Receta>

    init {
        initdata()
    }

    fun initdata(){

        listRecetas = DaoRecetas.myDao.getDataReceta().toMutableList()
    }

    fun loggOut(){
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()
        listRecetas.forEach{
            println(it)
        }
    }

    fun eliminarReceta(receta: Receta, recetas: MutableList<Receta>, adapter: AdapterReceta){

        val dialog = DeleteDialog()
        dialog.showConfirmationDialog(
            context,
            onConfirm = {
                DaoRecetas.myDao.removeReceta(receta)
                val position = recetas.indexOf(receta)
                recetas.removeAt(position)
                adapter.notifyItemRemoved(position)
            },
            onCancel = {

            }
        )
    }

    fun editarReceta(receta :Receta, recetas: MutableList<Receta>, adapter: AdapterReceta){
        val dialog = EditDialog()
        dialog.showEditDialog(context, receta,
            onConfirm = { recetaActualizada ->
                val position = recetas.indexOf(receta)
                DaoRecetas.myDao.editReceta(recetaActualizada,recetas, position)
                adapter.notifyItemChanged(position)
            }
        )
    }

    fun addReceta(recetas: MutableList<Receta>,adapter: AdapterReceta){
        val dialog = AddDialog()
        dialog.showAddDialog(context,
            onConfirm = { nuevaReceta ->
                DaoRecetas.myDao.addReceta(nuevaReceta, recetas)
                adapter.notifyItemInserted(recetas.size)

            }
        )
    }

}