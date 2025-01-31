package com.antonio.proyecto

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val toolbar = binding.appBarLayoutDrawer.toolbar

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController


        val navView = binding.myNavView


        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentHome, R.id.fragmentList), // Destinos principales
            binding.main // DrawerLayout
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
    }

    override fun onSupportNavigateUp(): Boolean{
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

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