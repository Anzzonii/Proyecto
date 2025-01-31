package com.antonio.proyecto.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonio.proyecto.R
import com.antonio.proyecto.models.Receta
import com.bumptech.glide.Glide

class AdapterReceta (
    private val recetasList : MutableList<Receta>,
    private val onDelete: (Receta) -> Unit,
    private val onEdit: (Receta) -> Unit,
    private val onAdd: () -> Unit,
    private val btnAdd : ImageView

) : RecyclerView.Adapter<AdapterReceta.RecetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)
        btnAdd.setOnClickListener{
            onAdd()
        }
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = recetasList[position]

        //Asignar nombre autor y nivel
        holder.titleTextView.text = receta.name
        holder.autorTextView.text = receta.autor
        holder.levelTextView.text = receta.level

        //Asignar imagen
        Glide.with(holder.itemView.context)
            .load(receta.image)
            .into(holder.imageView)

        holder.deleteImageView.setOnClickListener{
            onDelete(receta)
        }

        holder.editImageView.setOnClickListener{
            onEdit(receta)
        }

    }

    override fun getItemCount(): Int {
        return recetasList.size
    }

    //ViewHolder para cada item del recyclerView
    class RecetaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.img_receta)
        val titleTextView : TextView = itemView.findViewById(R.id.txtview_name)
        val autorTextView : TextView = itemView.findViewById(R.id.txtview_autor)
        val levelTextView : TextView = itemView.findViewById(R.id.txtview_level)
        val deleteImageView : ImageView = itemView.findViewById(R.id.btn_delete)
        val editImageView : ImageView = itemView.findViewById(R.id.btn_edit)
    }



}