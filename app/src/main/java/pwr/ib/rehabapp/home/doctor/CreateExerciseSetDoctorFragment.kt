package pwr.ib.rehabapp.home.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.create_exercise_set_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.profile.patient.CreateExerciseSetFragmentDirections
import pwr.ib.rehabapp.profile.patient.ExercisesToAddAdapter
import pwr.ib.rehabapp.profile.patient.OnExerciseItemLongClick

class CreateExerciseSetDoctorFragment: Fragment(), OnExerciseItemLongClick {

    private val adapter = ExercisesToAddAdapter(this)
    private val homeVm: HomePatientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_exercise_set_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_exercises.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_exercises.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindData()

        buttonConfirm.setOnClickListener{ creatingNewSet() }
    }

    override fun onExerciseLongClick(exercise: Exercise, position: Int) {
        bindData()
    }

    private fun creatingNewSet(){

        var newSet = ExerciseSet(
            null,
            etName.text.toString(),
            adapter.exerciseSelected,
            "high")

        val newSid = homeVm.addNewSet(newSet);
        newSet.sid = newSid

        val action = CreateExerciseSetDoctorFragmentDirections.actionCreateExerciseSetDoctorFragmentToNewSetCreatedDoctorFragment(newSet)
        findNavController().navigate(action)
    }

    private fun bindData(){

        homeVm.getAllExercises().observe(viewLifecycleOwner) { list ->
            adapter.setExercises(list)
        }
    }



}