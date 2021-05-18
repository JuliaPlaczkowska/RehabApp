package pwr.ib.rehabapp.home.patient

import androidx.lifecycle.ViewModel
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.repository.FirebaseRepository

class HomePatientViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    val exerciseSet = repository.getExerciseSetData()

    fun addExerciseSet(exerciseSet : ExerciseSet){

        repository.addExerciseSet(exerciseSet)
    }
}