package com.antonio.proyecto.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.antonio.proyecto.R
import com.antonio.proyecto.adapter.controller
import com.antonio.proyecto.databinding.FragmentRecipeBinding
import com.antonio.proyecto.dialogues.EditDialog
import com.antonio.proyecto.models.Receta
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class FragmentRecipe : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var receta: Receta

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recibo por argumentos los datos de la receta
        receta = arguments?.getSerializable("receta") as Receta

        receta.let {
            // Mostrar los datos de la receta
            binding.txtNombreReceta.text = it.name
            binding.txtAutorReceta.text = "Autor: ${it.autor}"
            binding.txtNivelReceta.text = "Nivel: ${it.level}"
            binding.txtIngredientes.text = it.ingredientes
            binding.txtElaboracion.text = it.elaboracion

            // Cargar la imagen con Glide
            Glide.with(this)
                .load(it.image)
                .into(binding.imgReceta)
        }

        //Si el usuario actual es el que creo la receta, este tiene las opciones de editar, de lo contrario no
        if(receta.correo == FirebaseAuth.getInstance().currentUser?.email.toString()) {
            setHasOptionsMenu(true)
        }

    }

    //Creo el menu y le indico que el de este fragment es el de toolbar_recipe
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_recipe, menu)  // Asegúrate de tener un archivo menu_recipe.xml
    }

    //Añado las funciones que tiene el elemento del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                // Aquí iría el código para editar la receta
                openEditDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Función que abre el edit de la receta para poder editarla
    private fun openEditDialog() {
        EditDialog().showEditDialog(
            requireContext(),
            receta,
            onConfirm = { updatedReceta ->
                controller.updateReceta(updatedReceta)
                updateUIWithReceta(updatedReceta)
            }
        )
    }

    //Función para actualizar la pantalla de la receta en el mismo momento ya que esta no se
    //actualiza de forma automatica
    private fun updateUIWithReceta(updatedReceta: Receta) {
        binding.txtNombreReceta.text = updatedReceta.name
        binding.txtAutorReceta.text = "Autor: ${updatedReceta.autor}"
        binding.txtNivelReceta.text = "Nivel: ${updatedReceta.level}"
        binding.txtIngredientes.text = updatedReceta.ingredientes
        binding.txtElaboracion.text = updatedReceta.elaboracion
        Glide.with(this).load(updatedReceta.image).into(binding.imgReceta)
    }
}