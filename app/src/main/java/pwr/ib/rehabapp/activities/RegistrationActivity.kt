package pwr.ib.rehabapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.patient.HomePatientViewModel

class RegistrationActivity : AppCompatActivity() {

    private val fbAuth = FirebaseAuth.getInstance()
    private val homeVm by viewModels<HomePatientViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    override fun onStart() {
        super.onStart()
        isCurrentUser()
    }

    private fun isCurrentUser() {
        fbAuth.currentUser?.let {
            homeVm.user().observe(this) { list ->
                list.let {
                    val user = it
                    if (user.role == "patient") {
                        helloPatient()
                    } else if (user.role == "doctor") {
                        helloDoc()
                    }
                }
            }
        }
    }

    private fun helloPatient() {
        fbAuth.currentUser?.let {
            val intent = Intent(applicationContext, MainPatientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun helloDoc() {
        fbAuth.currentUser?.let {
            val intent = Intent(applicationContext, MainDoctorActivity::class.java)
            startActivity(intent)
        }
    }
}


