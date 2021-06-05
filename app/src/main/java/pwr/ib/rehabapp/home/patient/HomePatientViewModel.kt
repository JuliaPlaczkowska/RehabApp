package pwr.ib.rehabapp.home.patient

import androidx.lifecycle.ViewModel
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.repository.FirebaseRepository

class HomePatientViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    val id = repository.getUserId()
    val exerciseSet = repository.getExerciseSetData()
    //val usersExerciseSet = repository.getUsersExerciseSets(id.map { it.toString() }.toList())

    fun addExerciseSet(exerciseSet : ExerciseSet){
        repository.addExerciseSet(exerciseSet)
    }
}