package pwr.ib.rehabapp.home.doctor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_doctor_fragment.*
import kotlinx.android.synthetic.main.profile_patient_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.home.patient.HomePatientFragmentDirections

class HomeDoctorFragment : Fragment() {

    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        fun newInstance() = HomeDoctorFragment()
    }

    private lateinit var viewModel: HomeDoctorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_doctor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeDoctorViewModel::class.java)

        buttonPatients.setOnClickListener{ goToPatientsListFragment()}
        buttonCreateSet.setOnClickListener{ createNewSet() }
        buttonLogout.setOnClickListener{ setupLogOutClick() }
    }

    private fun goToPatientsListFragment() {
        val action = HomeDoctorFragmentDirections.actionHomeDoctorFragmentToPatientsListFragment()
        findNavController().navigate(action)
    }

    private fun createNewSet() {
        val action = HomeDoctorFragmentDirections.actionHomeDoctorFragmentToCreateExerciseSetDoctorFragment()
        findNavController().navigate(action)
    }

    private fun setupLogOutClick() {
        buttonLogout.setOnClickListener {
            fbAuth.signOut()
            requireActivity().finish()

        }
    }
}