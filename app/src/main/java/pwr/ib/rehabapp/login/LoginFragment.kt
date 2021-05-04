package pwr.ib.rehabapp.login

//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.google.android.material.snackbar.Snackbar
//import com.google.android.material.textfield.TextInputEditText
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.fragment_sign_in.*
//import pwr.ib.rehabapp.BaseFragment
//import pwr.ib.rehabapp.R
//import pwr.ib.rehabapp.activities.MainPatientActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import pwr.ib.rehabapp.BaseFragment
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.activities.MainPatientActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class LoginFragment : BaseFragment() {

    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginClick()
        setupRegistrationClick()
    }

    private fun setupRegistrationClick() {

        signUpButton.setOnClickListener {

            findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment().actionId)
        }
    }

    private fun setupLoginClick() {
        loginButton.setOnClickListener {
            val email = emailLoginInput?.text?.trim().toString()
            val pass = passLoginInput?.text?.trim().toString()

            if (!email.isNullOrEmpty() || !pass.isNullOrEmpty()) {

                fbAuth.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener { authRes ->
                        if (authRes.user != null) startApp()
                    }
                    .addOnFailureListener { exc ->
                        Snackbar.make(
                            requireView(),
                            "Upss...Something went wrong...",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                        Log.d(LOG_DEBUG, exc.message.toString())
                    }


            }
        }


    }
}