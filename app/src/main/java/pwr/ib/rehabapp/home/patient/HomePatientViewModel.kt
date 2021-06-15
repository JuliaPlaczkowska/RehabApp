package pwr.ib.rehabapp.home.patient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import pwr.ib.rehabapp.data.*
import pwr.ib.rehabapp.repository.FirebaseRepository

class HomePatientViewModel : ViewModel() {

    private val repository = FirebaseRepository()
    fun user():LiveData<User> = repository.getUserData()
    val id = repository.getUserId()


    fun exerciseSet(): LiveData<List<ExerciseSet>> {
        return repository.getExerciseSetData()
    }


    fun favSets(): LiveData<List<ExerciseSet>> {
        return user().switchMap {
            repository.getFavSets(it.exercise_set)
        }
    }

    fun addUser() {
        repository.registerUser();
    }

    fun addExerciseSet(exerciseSet: ExerciseSet) {
        repository.addExerciseSet(exerciseSet)
    }

    fun getExercises(exerciseSet: ExerciseSet): LiveData<List<Exercise>> {

        val liveSet: LiveData<ExerciseSet> = MutableLiveData<ExerciseSet>(exerciseSet)

        return liveSet.switchMap {
            repository.getExercises(it.exercises)
        }
    }

    fun getExercisesByIds(eids : List<String>): LiveData<List<Exercise>> {

        return repository.getExercises(eids)

    }

    fun getAllExercises(): LiveData<List<Exercise>> {
        return repository.getAllExercises()
    }

    fun addExercisePerformed(exercisePerformed: ExercisePerformed): String? {
        val id = repository.addExercisePerformed(exercisePerformed)
        Log.d("VM_DEBUG", "epid from repo: $id")
        return id
    }

    fun addWorkout(workout: Workout): LiveData<String> {
        return repository.addWorkout(workout)
    }

    fun addNewSet(exerciseSet: ExerciseSet): String {
        return repository.addNewSet(exerciseSet)
    }

    fun getWorkouts(): LiveData<List<Workout>> {
        return repository.getWorkouts()
    }

    fun getSetById(sid: String ): LiveData<ExerciseSet> {
        return repository.getSetById(sid)
    }

    fun getWorkoutsByUserId(uid: String): LiveData<List<Workout>> {
        return repository.getWorkoutsByUserId(uid)
    }

    fun getExercisesPerformedByWorkout(epids: List<String>): LiveData<List<ExercisePerformed>>{
        return repository.getExercisesPerformedByWorkout(epids)
    }

    fun getPatients(): LiveData<List<User>> {
        return repository.getPatients()
    }

}