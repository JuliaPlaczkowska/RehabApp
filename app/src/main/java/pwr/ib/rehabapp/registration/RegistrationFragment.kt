package pwr.ib.rehabapp.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import pwr.ib.rehabapp.BaseFragment
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.home.patient.HomePatientViewModel

class RegistrationFragment : BaseFragment() {

    private val REG_DEBUG = "REG_DEBUG"
    private val fbAuth = FirebaseAuth.getInstance()
    private val homeVm: HomePatientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUpClick()
    }

    private fun setupSignUpClick() {
        signUpButtonRegistration.setOnClickListener {
            val email = email_registration.text?.trim().toString()
            val pass = pass_registration.text?.trim().toString()
            val repeatPass = repeat_pass_registration.text?.trim().toString()


            if (pass == repeatPass && !email.isNullOrEmpty() && !pass.isNullOrEmpty()) {
                fbAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener { authRes ->
                        if (authRes.user != null) {

                            homeVm.addUser()

                            startApp()
                        }
                    }
                    .addOnFailureListener { exception ->

                        if(exception.message.toString() == "The email address is badly formatted.")
                        Snackbar.make(
                            requireView(),
                            "Please enter valid email address",
                            Snackbar.LENGTH_SHORT
                        ).show()

                        else if(exception.message.toString() == "The given password is invalid. [ Password should be at least 6 characters ]")
                            Snackbar.make(
                                requireView(),
                                "Password should be at least 6 characters",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        Log.d(REG_DEBUG, exception.message.toString())

                    }
            } else if (email.isNullOrEmpty()) {
                Snackbar.make(
                    requireView(),
                    "Please enter your email address",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (pass.isNullOrEmpty()) {
                Snackbar.make(
                    requireView(),
                    "Please enter your password",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (repeatPass.isNullOrEmpty()) {
                Snackbar.make(
                    requireView(),
                    "Please repeat your password",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (pass != repeatPass) {
                Snackbar.make(
                    requireView(),
                    "Passwords do not match",
                    Snackbar.LENGTH_SHORT
                ).show()
            }


        }
    }


}