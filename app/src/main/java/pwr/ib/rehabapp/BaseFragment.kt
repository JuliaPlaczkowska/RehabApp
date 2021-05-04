package pwr.ib.rehabapp

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import pwr.ib.rehabapp.activities.MainPatientActivity


//klasa abstrakcyjna dziedzicząca [p Fragment, żeby nie kopiować wiele razy intencji
abstract class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transInflater = TransitionInflater.from(requireContext())
        enterTransition = transInflater.inflateTransition(R.transition.slide_right)
        exitTransition = transInflater.inflateTransition(R.transition.fade_out)
    }

    protected fun startApp(){
        val intent = Intent(requireContext(), MainPatientActivity::class.java)
        startActivity(intent)
    }

}