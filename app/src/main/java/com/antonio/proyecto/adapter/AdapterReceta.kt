package com.antonio.proyecto.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.antonio.proyecto.R
import com.antonio.proyecto.controller.Controller
import com.antonio.proyecto.dialogues.DeleteDialog
import com.antonio.proyecto.dialogues.EditDialog
import com.antonio.proyecto.models.Receta
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

lateinit var controller: Controller

private val recetasList : MutableList<Receta> = mutableListOf()


class AdapterReceta(
    val controllerList: Controller,
    private val navController: NavController

    ) : RecyclerView.Adapter<AdapterReceta.RecetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)

        controller = controllerList

        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {

        val usuarioActual = FirebaseAuth.getInstance().currentUser?.email.toString()

        val receta = recetasList[position]


        //Asignar nombre autor y nivel
        holder.titleTextView.text = receta.name
        holder.autorTextView.text = receta.autor
        holder.levelTextView.text = receta.level




        //Asignar al card un listener para ver la receta
        holder.card.setOnClickListener{
            //Creo un contenedor con los datos de la receta que voy a pasar a los detalles de esta
            val bundle = android.os.Bundle().apply {
                putSerializable("receta", receta)
            }

            navController.navigate(R.id.fragmentRecipe, bundle)
        }

        if(receta.correo == usuarioActual) {
            //Boton de eliminacion de la receta
            holder.deleteImageView.setOnClickListener {

                //Muestra el dialog de borrar que devuelve una receta, si se confirma se borra en el controller
                DeleteDialog().showConfirmationDialog(
                    holder.itemView.context,
                    onConfirm = {
                        println("AAAAAAAAAAAAAAAAAAA"+receta.id)
                        controller.deleteReceta(receta)
                    }
                )
            }

        }else{
            holder.deleteImageView.visibility = View.GONE
        }

        //Asignar imagen
        Glide.with(holder.itemView.context)
            .load(receta.image)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return recetasList.size
    }


    fun submitList(lista: List<Receta>?) {

        recetasList.clear()

        if (lista != null) {
            recetasList.addAll(lista)
        }

        notifyDataSetChanged()

    }

    //ViewHolder para cada item del recyclerView
    class RecetaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val card : CardView = itemView.findViewById(R.id.item_receta)
        val imageView : ImageView = itemView.findViewById(R.id.img_receta)
        val titleTextView : TextView = itemView.findViewById(R.id.txtview_name)
        val autorTextView : TextView = itemView.findViewById(R.id.txtview_autor)
        val levelTextView : TextView = itemView.findViewById(R.id.txtview_level)
        val deleteImageView : ImageView = itemView.findViewById(R.id.btn_delete)
    }



}