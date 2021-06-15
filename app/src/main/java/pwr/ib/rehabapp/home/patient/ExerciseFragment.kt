package pwr.ib.rehabapp.home.patient

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.exercise_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExercisePerformed
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout
import java.time.LocalDateTime

class ExerciseFragment : Fragment() {

    private val homeVm by viewModels<HomePatientViewModel>()
    private var set = ExerciseSet()
    private val workout = Workout()
    private var count: Int = 0
    private var exercises: List<Exercise>? = null
    private var exercisePerformed: ExercisePerformed = ExercisePerformed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        set = this.arguments?.get("set") as ExerciseSet
        workout.uid = homeVm.id
        workout.sid = set.sid
        workout.name = set.name
        workout.exercises = emptyList()
        workout.start_date = LocalDateTime.now().toString()
        homeVm.getExercises(set).observe(viewLifecycleOwner) { list ->
            list.let {
                exercises = list
            }
            bindData()
        }

        buttonBad.setOnClickListener { setFeedback("bad") }
        buttonGood.setOnClickListener { setFeedback("good") }
        buttonSkip.setOnClickListener { nextExercise(true) }
        buttonNext.setOnClickListener { nextExercise(false) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData() {

        Log.d("EXERCISE_FRAGMENT_DEBUG", "IM IN BINDDATA for index : $count")

        exercisePerformed.eid = exercises?.get(count)?.eid
        exercisePerformed.start_date = LocalDateTime.now().toString()

        textName.text = exercises?.get(count)?.name
        exercisePerformed.name = exercises?.get(count)?.name
        //imageView
        textDescription.text = exercises?.get(count)?.description
        exercisePerformed.description=exercises?.get(count)?.description
        textDuration.text = exercises?.get(count)?.reps
        exercisePerformed.reps = exercises?.get(count)?.reps

        Glide.with(this)
            .load(exercises?.get(count)?.image)
            .into(imageView)

        exercisePerformed.image = exercises?.get(count)?.image

    }

    private fun setFeedback(value: String) {
        exercisePerformed.feedback = value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextExercise(skipped: Boolean) {


        exercisePerformed.skipped = skipped
        exercisePerformed.end_date = LocalDateTime.now().toString()
        val epid = homeVm.addExercisePerformed(exercisePerformed)
        Log.d("EXERCISE_FRAGMENT_DEBUG", "EPID: $epid")

        workout.exercises = workout.exercises!!.plus(epid.toString())

        if (count + 1 < exercises?.size!!) {
            count += 1
            exercisePerformed = ExercisePerformed()
            bindData()
        } else {
            workout.end_date = LocalDateTime.now().toString()
            finishWorkout()
        }
    }

    private fun finishWorkout() {

        homeVm.addWorkout(workout)

        val action = ExerciseFragmentDirections
            .actionExerciseFragmentToSetFinishedFragment(set, workout)

        findNavController().navigate(action)
    }
}