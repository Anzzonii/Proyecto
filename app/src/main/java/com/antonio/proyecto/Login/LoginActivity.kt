package com.antonio.proyecto.Login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.antonio.proyecto.MainActivity
import com.antonio.proyecto.R
import com.antonio.proyecto.dialogues.RegisterDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    object Global{
        var preferencias_compartidas="sharedpreferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        verificar_sesion_abierta()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tRestaurarPass = findViewById<TextView>(R.id.tRestaurarPass)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        var correo = findViewById<TextInputLayout>(R.id.user)
        var pass = findViewById<TextInputLayout>(R.id.password)
        val btnRegister = findViewById<AppCompatButton>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            if(pass.editText?.text.toString()!=""){
                if(correo.editText?.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.editText?.text.toString()).matches()){
                    login_firebase(correo.editText?.text.toString(), pass.editText?.text.toString())
                }else{
                    Log.d("TAG", "correo: "+correo)
                    Toast.makeText(this, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Introduce contraseña", Toast.LENGTH_LONG).show()
            }

        }

        btnRegister.setOnClickListener {
            RegisterDialog().show(supportFragmentManager, null)
        }

        tRestaurarPass.setOnClickListener {
            if(correo.editText?.text.toString()!=""){
                if(Patterns.EMAIL_ADDRESS.matcher(correo.editText?.text.toString()).matches()){
                    resetPassword(correo.editText?.text.toString())
                }else{
                    Toast.makeText(this, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Introduce un correo", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun login_firebase(correo: String, pass: String){
        val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if(user != null && user.isEmailVerified) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("Correo", correo)
                            intent.putExtra("Proveedor", "Usuario/Contraseña")
                            startActivity(intent);

                            //Guarda el inicio de sesion
                            guardar_sesion(task.result.user?.email.toString(), "Usuario/Contraseña")

                        }else{
                            Toast.makeText(this, "Debes verificar tu correo antes de iniciar sesión.", Toast.LENGTH_LONG).show()
                            auth.signOut()
                        }
                    } else {
                        Toast.makeText(this, "Usuario/Contraseña incorrecto(s)", Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun resetPassword(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Correo no existente", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun verificar_sesion_abierta(){
        var sesion_abierta:SharedPreferences=this.getSharedPreferences(Global.preferencias_compartidas, Context.MODE_PRIVATE)

        var correo = sesion_abierta.getString("correo", null)
        var proveedor = sesion_abierta.getString("Proveedor", null)
        if(correo!=null && proveedor!=null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Correo", correo)
            intent.putExtra("Proveedor", proveedor)
            startActivity(intent);
        }
    }

    fun guardar_sesion(correo: String, proveedor: String){
        var guardar_sesion:SharedPreferences.Editor=this.getSharedPreferences(Global.preferencias_compartidas, Context.MODE_PRIVATE).edit()
        guardar_sesion.putString("correo", correo)
        guardar_sesion.putString("Proveedor", proveedor)
        guardar_sesion.apply()
        guardar_sesion.commit()

    }

}