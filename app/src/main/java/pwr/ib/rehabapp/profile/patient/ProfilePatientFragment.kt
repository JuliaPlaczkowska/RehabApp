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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile_patient_fragment.*
import pwr.ib.rehabapp.BaseFragment
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.User

class ProfilePatientFragment : BaseFragment() {

    private val PROFILE_DEBUG = "PROFILE_DEBUG"
    private val REQUEST_IMAGE_CAPTURE = 1
    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val profileVm by viewModels<ProfilePatientViewModel>()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(PROFILE_DEBUG, "profile patient view created")


        return inflater.inflate(R.layout.profile_patient_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileVm.user.observe(viewLifecycleOwner) { user ->
            bindUserData(user)
            setupLogOutClick()
        }
    }

    private fun bindUserData(user: User) {
        Log.d(PROFILE_DEBUG, user.toString())
        userSurnameET.setText(user.surname)
        userHeightET.text = user.height.toString()
        userWeightET.text = user.weight.toString()
//        Glide.with(this)
//            .load(user.image)
//            .circleCrop()
//            .into(userImage)
    }

    private fun setupLogOutClick() {
        buttonLogOut.setOnClickListener {
            fbAuth.signOut()
            requireActivity().finish()

        }
    }

    private fun setupStartExerciseClick() {
        buttonStartExercise.setOnClickListener {

        }
    }
}