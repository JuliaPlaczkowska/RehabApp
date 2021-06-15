package pwr.ib.rehabapp.home.patient


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.set_finished_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout
import java.time.LocalDateTime
import java.time.Period

class SetFinishedFragment : Fragment() {

    private val homeVm by viewModels<HomePatientViewModel>()
    private var set = ExerciseSet()
    private var workout = Workout()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_finished_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        set = this.arguments?.get("set") as ExerciseSet
        workout = this.arguments?.get("workout") as Workout

        textSetName.text = "You have completed ${set.name}"

        val startDate : LocalDateTime = LocalDateTime.parse(workout.start_date)
        val endDate : LocalDateTime = LocalDateTime.parse(workout.end_date)

        var min = endDate.minute - startDate.minute
        var sec = endDate.second - startDate.second

        if(sec < 0){
            min -= min
            sec += 60
        }
        textTime.text = "in $min min $sec sec"

        buttonNextWorkout.setOnClickListener{ nextWorkout() }
        buttonProgress.setOnClickListener{ showProgress() }
    }

    private fun nextWorkout() {

        val action = SetFinishedFragmentDirections
            .actionSetFinishedFragmentToHomePatientFragment()

        findNavController().navigate(action)
    }

    private fun showProgress(){
        val action = SetFinishedFragmentDirections
            .actionSetFinishedFragmentToProgressFragment()

        findNavController().navigate(action)
    }
}