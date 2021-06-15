package pwr.ib.rehabapp.profile.patient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile_patient_fragment.*
import pwr.ib.rehabapp.BaseFragment
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.patient.SetFinishedFragmentDirections

class ProfilePatientFragment : BaseFragment() {

    private val PROFILE_DEBUG = "PROFILE_DEBUG"
    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val profileVm by viewModels<ProfilePatientViewModel>()
    var user : User = User()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(PROFILE_DEBUG, "profile patient view created")


        return inflater.inflate(R.layout.profile_patient_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileVm.user().observe(viewLifecycleOwner) { u ->
            user = u
            bindUserData(user)
            setupLogOutClick()
        }
        setupSubmitClick()
        buttonProgress.setOnClickListener{ showProgress() }
        buttonAddExerciseSet.setOnClickListener{ addExerciseSet() }
        buttonCreateExerciseSet.setOnClickListener{ createExeciseSet()}
    }

    private fun createExeciseSet() {
        val action = ProfilePatientFragmentDirections.actionProfilePatientFragmentToCreateExerciseSetFragment()
        findNavController().navigate(action)
    }

    private fun addExerciseSet() {
        val action = ProfilePatientFragmentDirections.actionProfilePatientFragmentToAddExerciseSetFragment()
        findNavController().navigate(action)
    }

    private fun bindUserData(user: User) {
        Log.d(PROFILE_DEBUG, user.toString())
        userSurnameET.setText(user.surname)
        userHeightET.hint = user.height.toString()
        userWeightET.hint = user.weight.toString()
    }

    private fun setupLogOutClick() {
        buttonLogOut.setOnClickListener {
            fbAuth.signOut()
            requireActivity().finish()

        }
    }

    private fun setupSubmitClick() {
        submitDataProfile.setOnClickListener {
            if(!userSurnameET.text.isNullOrEmpty())
            user.surname = userSurnameET.text.toString()

            if(!userHeightET.text.isNullOrEmpty())
                user.height = userHeightET.text.toString().toFloat()

            if(!userWeightET.text.isNullOrEmpty())
                user.weight = userWeightET.text.toString().toFloat()

            profileVm.updateUserDetails(user)
            Snackbar.make(
                it,
                "Your details are up to date",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun showProgress(){
        val action = ProfilePatientFragmentDirections
            .actionProfilePatientFragmentToProgressFragment()

        findNavController().navigate(action)
    }
}