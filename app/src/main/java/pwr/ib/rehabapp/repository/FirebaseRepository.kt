package pwr.ib.rehabapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import pwr.ib.rehabapp.data.*
import java.util.*

class FirebaseRepository {

    private val REPO_DEBUG = "REPO_DEBUG"

    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()


    fun getUserData(): LiveData<User> {
        val cloudResult = MutableLiveData<User>()
        val uid = auth.currentUser?.uid

        cloud.collection("user")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult
    }

    fun getUserId(): String {
        return auth.currentUser?.uid.toString()
    }

    fun registerUser(){
        val uid = auth.currentUser!!.uid
        val user : User = User(uid, "9DH9D5mgkQang8vMDpkM9sbuGAD3","patient")

        cloud.collection("user")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "user added successfully with uid: $uid")
            }.addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    fun getExerciseSetData(): LiveData<List<ExerciseSet>> {
        val cloudResult = MutableLiveData<List<ExerciseSet>>()

        cloud.collection("exercise_set")
            .get()
            .addOnSuccessListener {
                val exerciseSet = it.toObjects(ExerciseSet::class.java)
                cloudResult.postValue(exerciseSet)
                Log.d(REPO_DEBUG, "sets count: ${exerciseSet.size}")
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult
    }


    fun addExerciseSet(exerciseSet: ExerciseSet) {
        cloud.collection("user")
            .document(auth.currentUser?.uid!!)
            .update("exercise_set", FieldValue.arrayUnion(exerciseSet.sid))
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Dodano nowy zestaw cwiczen")
                Log.d(REPO_DEBUG, "lista cwiczen: " + exerciseSet.exercises.toString())
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    fun getFavSets(list: List<String>?): LiveData<List<ExerciseSet>> {
        val cloudResult = MutableLiveData<List<ExerciseSet>>()
        Log.d(REPO_DEBUG, "correct ids list : " + list.toString())
        if (!list.isNullOrEmpty())
            cloud.collection("exercise_set")
                .whereIn("sid", list)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(ExerciseSet::class.java)
                    cloudResult.postValue(resultList)
                    Log.d(REPO_DEBUG, "fav sets: ${resultList.size}")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
        return cloudResult
    }

    fun getExercises(eids: List<String>?): LiveData<List<Exercise>> {
        val cloudResult = MutableLiveData<List<Exercise>>()
        if (!eids.isNullOrEmpty()) {
            cloud.collection("exercise")
                .whereIn("eid", eids)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(Exercise::class.java)
                    cloudResult.postValue(resultList)
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
            Log.d(REPO_DEBUG, "lista cwiczen NIE JEST pusta i dlugosc rowna:" + eids.size)

        } else
            Log.d(REPO_DEBUG, "lista cwiczen pusta")
        return cloudResult
    }

    fun getAllExercises(): LiveData<List<Exercise>> {
        val cloudResult = MutableLiveData<List<Exercise>>()
        cloud.collection("exercise")
            .get()
            .addOnSuccessListener {
                val resultList = it.toObjects(Exercise::class.java)
                cloudResult.postValue(resultList)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult
    }

    fun addExercisePerformed(exercisePerformed: ExercisePerformed): String? {
        var id: String = UUID.randomUUID().toString()
        exercisePerformed.epid = id
        cloud.collection("exercise_performed")
            .document(id)
            .set(exercisePerformed)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "exercise performed added successfully with id: $id")
            }.addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return id
    }

    fun addWorkout(workout: Workout): LiveData<String> {
        val cloudResult = MutableLiveData<String>()

        cloud.collection("workout")
            .add(workout)
            .addOnSuccessListener {
                val result = it.id
                cloudResult.postValue(result)
                Log.d(REPO_DEBUG, "workout added successfully with id: $result")
            }.addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult
    }

    fun addNewSet(exerciseSet: ExerciseSet): String {
        var id: String = UUID.randomUUID().toString()
        exerciseSet.sid = id
        cloud.collection("exercise_set")
            .document(id)
            .set(exerciseSet)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, id)
            }.addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return id
    }

    fun getWorkouts(): LiveData<List<Workout>> {

        val cloudResult = MutableLiveData<List<Workout>>()

        val uid: List<String?> = listOf(auth.currentUser?.uid)

        cloud.collection("workout")
            .whereIn("uid", uid)
            .get()
            .addOnSuccessListener {
                val workout = it.toObjects(Workout::class.java)
                cloudResult.postValue(workout)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult

    }

    fun getWorkoutsByUserId(uid: String): LiveData<List<Workout>> {

        val cloudResult = MutableLiveData<List<Workout>>()

        val uid: List<String?> = listOf(uid)

        cloud.collection("workout")
            .whereIn("uid", uid)
            .get()
            .addOnSuccessListener {
                val workout = it.toObjects(Workout::class.java)
                cloudResult.postValue(workout)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

        return cloudResult

    }

    fun getPatients(): LiveData<List<User>> {

        val cloudResult = MutableLiveData<List<User>>()

        val did: List<String?> = listOf(auth.currentUser?.uid)

        cloud.collection("user")
            .whereIn("did", did)
            .get()
            .addOnSuccessListener {
                val patients = it.toObjects(User::class.java)
                cloudResult.postValue(patients)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return cloudResult
    }

    fun getExercisesPerformedByWorkout(list: List<String>): LiveData<List<ExercisePerformed>> {

        val cloudResult = MutableLiveData<List<ExercisePerformed>>()
        Log.d(REPO_DEBUG, "epids by set: ${list}")
        if (!list.isNullOrEmpty())
            cloud.collection("exercise_performed")
                .whereIn("epid", list)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(ExercisePerformed::class.java)
                    cloudResult.postValue(resultList)
                    Log.d(REPO_DEBUG, "ep by set: ${resultList.size}")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }

        return cloudResult
    }

    fun getSetById(sid: String): LiveData<ExerciseSet> {
        val list = listOf<String>(sid)
        val cloudResult = MutableLiveData<ExerciseSet>()

        if (!list.isNullOrEmpty())
            cloud.collection("exercise_set")
                .whereIn("sid", list)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(ExerciseSet::class.java)
                    cloudResult.postValue(resultList.last())
                    Log.d(REPO_DEBUG, "fav sets: ${resultList.size}")
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }

        return cloudResult
    }

     fun updateUserDetails(user: User) {
        cloud.collection("user")
            .document(auth.currentUser!!.uid)
            .update("surname", user.surname, "height", user.height, "weight", user.weight)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "UPDATE USER PHOTO!")
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

}