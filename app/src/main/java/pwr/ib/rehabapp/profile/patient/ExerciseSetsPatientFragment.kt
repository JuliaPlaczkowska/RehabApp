package pwr.ib.rehabapp.profile.patient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_patient_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.home.patient.ExerciseSetAdapter
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.home.patient.OnExerciseSetItemLongClick

class ExerciseSetsPatientFragment: Fragment(), OnExerciseSetItemLongClick {

    private val auth = FirebaseAuth.getInstance()
    private val homeVm by viewModels<HomePatientViewModel>()
    private val adapter = ExerciseSetAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_patient_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_home.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_home.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeVm.exerciseSet.observe(viewLifecycleOwner) { list ->
            adapter.setExerciseSets(list)
        }
    }

    override fun onExerciseSetLongClick(exerciseSet: ExerciseSet, position: Int) {
        homeVm.addExerciseSet(exerciseSet)
    }
}