package pwr.ib.rehabapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User

class FirebaseRepository {

    private val REPO_DEBUG = "REPO_DEBUG"

    private val storage = FirebaseStorage.getInstance()
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

    fun getExerciseSetData(): LiveData<List<ExerciseSet>> {
        val cloudResult = MutableLiveData<List<ExerciseSet>>()

        cloud.collection("exercise_set")
            .get()
            .addOnSuccessListener {
                val exerciseSet = it.toObjects(ExerciseSet::class.java)
                cloudResult.postValue(exerciseSet)
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
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }

    }

}