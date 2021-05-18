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
import kotlinx.android.synthetic.main.exercise_sets_patient_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.home.patient.ExerciseSetAdapter
import pwr.ib.rehabapp.home.patient.OnExerciseSetItemLongClick

class ExerciseSetsPatientFragment: Fragment(), OnExerciseSetItemLongClick {

    private val PROFILE_DEBUG = "PROFILE_DEBUG"
    private val REQUEST_IMAGE_CAPTURE = 1

    private val profileVm by viewModels<ProfilePatientViewModel>()
    private val adapter = ExerciseSetAdapter(this)

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_sets_patient_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerExerciseSets.layoutManager=LinearLayoutManager(requireContext())
        recyclerExerciseSets.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileVm.user.observe(viewLifecycleOwner) { user ->

        }
        profileVm.usersExerciseSet.observe(viewLifecycleOwner) { list ->
            list?.let{
                adapter.setExerciseSets(it)
            }
        }

    }

    override fun onExerciseSetLongClick(exerciseSet: ExerciseSet, position: Int) {

    }
}