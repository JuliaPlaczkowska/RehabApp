package pwr.ib.rehabapp.profile.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import pwr.ib.rehabapp.repository.FirebaseRepository

class ProfilePatientViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    val user = repository.getUserData()
    val usersExerciseSet = user.switchMap {
        repository.getUsersExerciseSets(it.exercise_set)
    }
}