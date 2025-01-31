package com.antonio.proyecto.dialogues

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.antonio.proyecto.MainActivity
import com.antonio.proyecto.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view:View = inflater.inflate(R.layout.fragment_register_dialog, container, false)

        val correo = view.findViewById<TextInputLayout>(R.id.regUser)
        val pass = view.findViewById<TextInputLayout>(R.id.regPass)
        val btnRegister = view.findViewById<AppCompatButton>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            if(pass.editText?.text.toString()!=""){
                if(correo.editText?.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.editText?.text.toString()).matches()){
                    crear_cuenta_firebase(correo.editText?.text.toString(), pass.editText?.text.toString())
                }else{
                    Toast.makeText(requireContext(), "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(requireContext(), "Introduce contraseña", Toast.LENGTH_LONG).show()
            }

        }


        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun crear_cuenta_firebase(correo:String, pass:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    val user = task.result.user
                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            Toast.makeText(requireContext(), "Cuenta creada. Verifica tu correo", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al enviar verificación", Toast.LENGTH_LONG).show()
                        }
                    }

                    Toast.makeText(requireContext(), "Cuenta creada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "contraseña corta/usuario existente", Toast.LENGTH_LONG).show()
                }
            }
    }
}