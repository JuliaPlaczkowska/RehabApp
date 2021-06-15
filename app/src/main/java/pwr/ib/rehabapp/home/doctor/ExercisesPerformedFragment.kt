package pwr.ib.rehabapp.home.doctor

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.exercises_performed_fragment.*
import kotlinx.android.synthetic.main.home_patient_fragment.*
import kotlinx.android.synthetic.main.set_finished_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExercisePerformed
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.home.patient.OnExerciseSetItemLongClick
import java.time.LocalDateTime

class ExercisesPerformedFragment : Fragment() {


    private val adapter = ExercisesPerformedAdapter()
    private val homeVm: HomePatientViewModel by activityViewModels()
    private var set = ExerciseSet()
    private var workout = Workout()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercises_performed_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_ep.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_ep.adapter = adapter


        workout = this.arguments?.get("workout") as Workout
        homeVm.getSetById(workout.sid!!).observe(viewLifecycleOwner) {
            set = it
            textSetName2.text = it.name
        }
        bindData()
        homeVm.user().observe(viewLifecycleOwner) {
            textPatientName.text = it.surname
        }


        textDate2.text = "${LocalDateTime.parse(workout.start_date).dayOfMonth} " +
                "${LocalDateTime.parse(workout.start_date).month}, " +
                "${LocalDateTime.parse(workout.start_date).year}"

        val startDate: LocalDateTime = LocalDateTime.parse(workout.start_date)
        val endDate: LocalDateTime = LocalDateTime.parse(workout.end_date)

        var min = endDate.minute - startDate.minute
        var sec = endDate.second - startDate.second

        if (sec < 0) {
            min -= min
            sec += 60
        }
        textTime3.text = "in $min min $sec sec"

    }

    private fun bindData() {

        var epList = listOf<ExercisePerformed>(ExercisePerformed())

        homeVm.getExercisesPerformedByWorkout(workout.exercises!!)
            .observe(viewLifecycleOwner) { list ->
                Log.d("EP_DEBUG", list.size.toString())
                if (!list.isNullOrEmpty())
                    adapter.setEp(list)
            }
    }




}