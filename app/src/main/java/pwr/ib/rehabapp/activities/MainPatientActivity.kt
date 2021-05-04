package pwr.ib.rehabapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_patient.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.loginButton
import pwr.ib.rehabapp.R

class MainPatientActivity :  AppCompatActivity() {

    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)
        setupLogOutClick()

    }


    private fun setupLogOutClick() {
        buttonLogOut.setOnClickListener {
            fbAuth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }

    }
}