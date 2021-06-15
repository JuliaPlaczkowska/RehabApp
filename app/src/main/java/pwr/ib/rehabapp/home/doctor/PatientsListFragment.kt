package pwr.ib.rehabapp.home.doctor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.exercise_list_fragment.*
import kotlinx.android.synthetic.main.patients_list_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.patient.*

class PatientsListFragment : Fragment(), OnPatientItemLongClick {

    private val auth = FirebaseAuth.getInstance()
    private val homeVm by viewModels<HomePatientViewModel>()
    private val adapter = PatientsAdapter(this)
    private var doctor = User()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patients_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_patients.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_patients.adapter = adapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeVm.user().observe(viewLifecycleOwner) { list ->
            list.let {
                doctor = it
            }
        }

        homeVm.getPatients().observe(viewLifecycleOwner) { list ->
            list.let {
                adapter.setPatients(it)
            }
        }
    }

    override fun onPatientLongClick(user: User, position: Int) {
        Log.d("PATIENTS_LIST_DEBUG", "long click on user: ${user.surname}")
        val action = PatientsListFragmentDirections
            .actionPatientsListFragmentToFeedbackFragment(user)
        findNavController().navigate(action)
    }
}