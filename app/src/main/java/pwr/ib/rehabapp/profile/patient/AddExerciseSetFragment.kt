package pwr.ib.rehabapp.profile.patient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.lifecycle.switchMap
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.add_exercise_set_fragment.*
import kotlinx.android.synthetic.main.home_patient_fragment.*
import kotlinx.android.synthetic.main.home_patient_fragment.recyclerView_home
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.home.patient.ExerciseSetAdapter
import pwr.ib.rehabapp.home.patient.HomePatientFragmentDirections
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.home.patient.OnExerciseSetItemLongClick

class AddExerciseSetFragment : Fragment(), OnExerciseSetItemLongClick {

    private val adapter = ExerciseSetAdapter(this)
    private val homeVm: HomePatientViewModel by activityViewModels()
    private val ADD_SET_DEBUG = "ADD_SET_DEBUG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_exercise_set_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_home.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_home.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindData()

        buttonNewSet.setOnClickListener { startCreatingNewSet() }
    }

    override fun onExerciseSetLongClick(exerciseSet: ExerciseSet, position: Int) {
        homeVm.addExerciseSet(exerciseSet)
        bindData()
        Snackbar.make(
            requireView(),
            "${exerciseSet.name} added",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun startCreatingNewSet() {
        val action =
            AddExerciseSetFragmentDirections.actionAddExerciseSetFragmentToCreateExerciseSetFragment()
        findNavController().navigate(action)
    }

    private fun bindData() {

        homeVm.exerciseSet().observe(viewLifecycleOwner) { exerciseSets ->
                    adapter.setExerciseSets(exerciseSets)
        }
    }

}