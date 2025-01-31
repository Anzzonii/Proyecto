package com.antonio.proyecto.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonio.proyecto.R
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.controller.Controller
import com.antonio.proyecto.databinding.FragmentListBinding
import com.antonio.proyecto.models.Receta

class FragmentList : Fragment() {

    lateinit var navController: NavController
    private lateinit var adapter : AdapterReceta
    private lateinit var recetas : MutableList<Receta>
    lateinit var bindingFragment: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflamos la vista, a partir de la clase Binding del fragmento.
        bindingFragment = FragmentListBinding   .inflate(
            inflater,
            container, false
        )
        val viewFragment = bindingFragment.root //devolvemos el raiz, queserá la vista.
        return viewFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navHost = requireActivity() //referencia del activity
            .supportFragmentManager //administrador de Fragmentos
            .findFragmentById(R.id.fragmentContainerView) //buscamos elContenedor de Fragmentos
        navHost.let {//Si entramos dentro, no es nulo.
            navController = navHost!!.findNavController() //buscamos su NavController

            val recycler = bindingFragment.myRecyclerView
            var controller : Controller = Controller(requireContext())
            val btnAdd : ImageView = bindingFragment.btnAdd

            recetas = controller.listRecetas

            adapter = AdapterReceta(
                recetas,
                onDelete = { receta -> controller.eliminarReceta(receta, recetas, adapter)},
                onEdit = {receta -> controller.editarReceta(receta, recetas, adapter)},
                onAdd = {controller.addReceta(recetas, adapter)},
                btnAdd
            )

            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = adapter


        }

    }



}