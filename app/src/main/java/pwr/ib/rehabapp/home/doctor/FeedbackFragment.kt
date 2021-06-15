package pwr.ib.rehabapp.home.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.progress_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.data.Workout
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.home.patient.OnShowDetailsClick
import pwr.ib.rehabapp.home.patient.WorkoutAdapter

class FeedbackFragment : Fragment(), OnShowDetailsClick {

    private val homeVm by viewModels<HomePatientViewModel>()
    private val adapter = WorkoutAdapter(this)
    private var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_workouts.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_workouts.adapter = adapter

        user = this.arguments?.get("user") as User

        textCongratulations.text = user.surname
        etHeight.hint = user.height.toString()
        etWeight.hint = user.weight.toString()

        homeVm.getWorkoutsByUserId(user.uid!!).observe(viewLifecycleOwner){ list ->
            list.let {
                textWorkoutsTotal.text = ("Workouts: "+ list.size)
                adapter.setWorkouts(it)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onShowDetailsClick(workout: Workout, position: Int) {
        val action = FeedbackFragmentDirections.actionFeedbackFragmentToExercisesPerformedFragment(workout)
        findNavController().navigate(action)
    }
}