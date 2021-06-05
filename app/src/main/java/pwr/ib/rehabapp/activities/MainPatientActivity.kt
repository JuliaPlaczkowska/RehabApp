package pwr.ib.rehabapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import pwr.ib.rehabapp.R

class MainPatientActivity :  AppCompatActivity() {

    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavView)

        val navController = findNavController(R.id.mainNavHost)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homePatientFragment, R.id.profilePatientFragment))

        //setupActionBarWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)


    }




}