package pwr.ib.rehabapp.profile.doctor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pwr.ib.rehabapp.R

class ProfileDoctorFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileDoctorFragment()
    }

    private lateinit var viewModel: ProfileDoctorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_doctor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileDoctorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}