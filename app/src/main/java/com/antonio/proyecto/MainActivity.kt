package com.antonio.proyecto

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.antonio.proyecto.R;
import com.antonio.proyecto.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Muestra el view
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Coge la toolbar del binding
        val toolbar = binding.appBarLayoutDrawer.toolbar

        //cogemos el navHost y al navController le asignamos el controller de este
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController


        //Coge el navView del binding
        val navView = binding.myNavView

        //Configuramos los layouts que tendran el menu en la toolbar
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentHome, R.id.fragmentList, R.id.fragmentMyList),
            binding.main
        )

        //insertamos la toolbar
        setSupportActionBar(toolbar)

        /*
        vincula el navController con el toolbar (titulo + hambuerguesa/retroceso)
        De esa forma, se puede navegar con la toolbar.
         */
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*
        vincula el menú lateral del drawer con el navController según. el nav_graph
        De esa forma, se puede navegar con la barra lateral izquierda.
         */
        navView.setupWithNavController(navController)

        //Cojo el navHeader del navView y en el apartado del email pongo el email con el que estamos logeados
        //en este momento
        val headerView = navView.getHeaderView(0)

        val userEmail = headerView.findViewById<TextView>(R.id.txt_email)
        userEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()

        //Media player aqui para que solo se ejecute al iniciar la aplicacion y logearse o estar logeado
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido)
        mediaPlayer.start()

    }

    //Metodo necesario para el mediaPlayer
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    //Configuración del navController en el menu
    override fun onSupportNavigateUp(): Boolean{
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Creación de las opciones de la toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_op, menu)
        return true
    }


    //Navegación del menú de opciones.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fragmentHome -> {
                navController.navigate(R.id.fragmentHome)
                true
            }
            R.id.fragmentList -> {
                navController.navigate(R.id.fragmentList)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}