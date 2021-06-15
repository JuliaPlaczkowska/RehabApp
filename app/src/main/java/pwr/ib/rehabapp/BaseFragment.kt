package pwr.ib.rehabapp

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import pwr.ib.rehabapp.activities.MainDoctorActivity
import pwr.ib.rehabapp.activities.MainPatientActivity
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.patient.HomePatientViewModel


//klasa abstrakcyjna dziedzicząca [p Fragment, żeby nie kopiować wiele razy intencji
abstract class BaseFragment: Fragment() {

    private val fbAuth = FirebaseAuth.getInstance()
    private val homeVm by viewModels<HomePatientViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transInflater = TransitionInflater.from(requireContext())
        enterTransition = transInflater.inflateTransition(R.transition.slide_right)
        exitTransition = transInflater.inflateTransition(R.transition.fade_out)
    }

    protected fun startApp(){

        homeVm.user().observe(this) { list ->
            list.let {
                val user = it
                Log.d("REG_DEBUG", "user role : ${user.role}")

                if (user.role == "patient"){
                    helloPatient()
                }
                else {
                    helloDoc()
                }
            }
        }
    }

    private fun helloPatient(){
        fbAuth.currentUser?.let {
            val intent = Intent(requireContext(), MainPatientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun helloDoc(){
        fbAuth.currentUser?.let {
            val intent = Intent(requireContext(), MainDoctorActivity::class.java)
            startActivity(intent)
        }
    }

}