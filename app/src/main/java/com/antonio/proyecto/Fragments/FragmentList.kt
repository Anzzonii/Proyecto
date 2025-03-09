package com.antonio.proyecto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonio.proyecto.R
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.controller.Controller
import com.antonio.proyecto.databinding.FragmentListBinding
import com.antonio.proyecto.dialogues.AddDialog
import com.antonio.proyecto.models.Receta


class FragmentList : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val controller: Controller by viewModels()
    private lateinit var adapterReceta: AdapterReceta

    //Creacion de la vista usando binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Habilitar el menú en este fragmento
        setHasOptionsMenu(true)

        // Configurar RecyclerView
        adapterReceta = AdapterReceta(controller, findNavController())
        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.myRecyclerView.adapter = adapterReceta

        // Observar datos de Firebase en tiempo real para iniciar los datos
        controller.recetas.observe(viewLifecycleOwner) { lista ->
            adapterReceta.submitList(lista)
        }
    }

    // Inflar el menú de la toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbarlist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Manejar el clic en el botón de añadir
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val addDialog = AddDialog()

                // Mostrar el dialog para añadir una nueva receta
                addDialog.showAddDialog(requireContext()) { nuevaReceta ->
                    controller.addReceta(nuevaReceta)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


