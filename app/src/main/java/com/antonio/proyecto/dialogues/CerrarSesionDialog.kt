package com.antonio.proyecto.dialogues

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.antonio.proyecto.Login.LoginActivity
import com.antonio.proyecto.Login.LoginActivity.Global
import com.antonio.proyecto.MainActivity
import com.antonio.proyecto.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess


class CerrarSesionDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Crea la vista que va tener el dialog
        var view:View = inflater.inflate(R.layout.fragment_cerrar_sesion_dialog, container, false)

        //Guarda los botones que hay en la vista
        val btnAccept = view.findViewById<AppCompatButton>(R.id.btnAccept)
        val btnCancel = view.findViewById<AppCompatButton>(R.id.btnCancel)

        //En caso de aceptar ejecutara las funciones cerrarSesion y borrarSesion y cerrara el programa
        btnAccept.setOnClickListener{
            cerrarSesion()
            borrar_sesion()
            requireActivity().finishAffinity()
        }

        //Sale de la app en caso de cancelar
        btnCancel.setOnClickListener{
            dismissNow()
        }

        return view

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    //Cierra la sesión que hay actualmente en la aplicación
    fun cerrarSesion(){
        FirebaseAuth.getInstance().signOut()
    }

    //Borra la sesión de las SharedPreferences para que al iniciar la app no vuelva a entrar con la cuenta guardada
     fun borrar_sesion(){
        var borrar_sesion: SharedPreferences.Editor=requireContext().getSharedPreferences(Global.preferencias_compartidas, Context.MODE_PRIVATE).edit()
        borrar_sesion.clear()
        borrar_sesion.apply()
        borrar_sesion.commit()
     }
}