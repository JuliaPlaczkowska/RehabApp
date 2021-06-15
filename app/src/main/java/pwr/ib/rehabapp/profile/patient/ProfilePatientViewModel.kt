package pwr.ib.rehabapp.profile.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.repository.FirebaseRepository

class ProfilePatientViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    fun user() = repository.getUserData()

    fun updateUserDetails(user: User){
        repository.updateUserDetails(user)
    }
}