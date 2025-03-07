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
import com.antonio.proyecto.databinding.FragmentMyListBinding
import com.antonio.proyecto.dialogues.AddDialog
import com.antonio.proyecto.models.Receta
import com.google.firebase.auth.FirebaseAuth


class FragmentMyList : Fragment() {

    private lateinit var binding: FragmentMyListBinding
    private val controller: Controller by viewModels()
    private lateinit var adapterReceta: AdapterReceta

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Habilitar el menú en este fragmento
        setHasOptionsMenu(true)

        // Obtener el correo del usuario actual
        val usuarioActual = FirebaseAuth.getInstance().currentUser?.email.toString()

        // Configurar RecyclerView
        adapterReceta = AdapterReceta(controller, findNavController())
        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.myRecyclerView.adapter = adapterReceta

        // Observar datos de Firebase en tiempo real para iniciar los datos
        controller.recetas.observe(viewLifecycleOwner) { lista ->
            // Filtrar las recetas por el correo del usuario actual
            val recetasFiltradas = lista.filter { it.correo == usuarioActual }

            // Pasar solo las recetas filtradas al adaptador
            adapterReceta.submitList(recetasFiltradas)
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
