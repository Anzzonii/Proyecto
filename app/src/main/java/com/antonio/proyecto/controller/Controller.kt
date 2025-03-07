package com.antonio.proyecto.controller

import android.content.Context
import android.widget.Toast
import com.antonio.proyecto.adapter.AdapterReceta
import com.antonio.proyecto.dao.DaoRecetas
import com.antonio.proyecto.dialogues.AddDialog
import com.antonio.proyecto.dialogues.DeleteDialog
import com.antonio.proyecto.dialogues.EditDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.proyecto.models.Receta
import com.antonio.proyecto.object_models.Repository
import kotlinx.coroutines.launch


class Controller : ViewModel() {
    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> get() = _recetas
    init {
        obtenerRecetas()
    }

    // Obtener datos desde Firebase en tiempo real
    private fun obtenerRecetas() {
        Repository.getRecetas { receta ->
            _recetas.postValue(receta)
        }
    }

    // Agregar una receta
    fun addReceta(receta: Receta) {
        viewModelScope.launch {
            Repository.addReceta(receta)
        }
    }
    // Actualizar receta
    fun updateReceta(receta: Receta) {
        viewModelScope.launch {
            Repository.updateReceta(receta)
        }
    }
    // Eliminar receta
    fun deleteReceta(receta: Receta) {

        viewModelScope.launch {
            Repository.deleteReceta(receta.id)
        }
    }
}
