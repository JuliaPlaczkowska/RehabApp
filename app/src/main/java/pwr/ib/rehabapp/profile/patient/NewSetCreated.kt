package pwr.ib.rehabapp.profile.patient

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.new_set_created.*
import kotlinx.android.synthetic.main.set_finished_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout
import pwr.ib.rehabapp.home.patient.SetFinishedFragmentDirections
import java.time.LocalDateTime


class NewSetCreated : Fragment() {

    private var set = ExerciseSet()
    private var workout = Workout()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_set_created, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        set = this.arguments?.get("set") as ExerciseSet

        textNewSetName.text = set.name

        buttonStartNewWorkout.setOnClickListener{ nextWorkout() }
    }

    private fun nextWorkout() {

        val action = NewSetCreatedDirections.actionNewSetCreatedToHomePatientFragment()

        findNavController().navigate(action)
    }

}