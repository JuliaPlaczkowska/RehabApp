package pwr.ib.rehabapp.home.patient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.exercise_list_fragment.*
import kotlinx.android.synthetic.main.profile_patient_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout


class ExerciseListFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private val homeVm by viewModels<HomePatientViewModel>()
    private val adapter = ExercisesAdapter()
    private var set = ExerciseSet()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_exercises.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_exercises.adapter = adapter
        set = this.arguments?.get("set") as ExerciseSet
       buttonStartWorkout.setOnClickListener { startWorkout() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("EXERCISE_LIST_DEBUG", "BITMAP: " + set.sid)
        super.onActivityCreated(savedInstanceState)
        Log.d("EXERCISE_LIST_DEBUG", "lista cwiczen: "+set.exercises.toString())
        homeVm.getExercises(set).observe(viewLifecycleOwner) { list ->
            list.let {
                adapter.setExercises(it)
            }
        }
    }

    private fun startWorkout(){
       val action = ExerciseListFragmentDirections
           .actionExerciseListFragment2ToExerciseFragment(set)

        findNavController().navigate(action)
    }
}