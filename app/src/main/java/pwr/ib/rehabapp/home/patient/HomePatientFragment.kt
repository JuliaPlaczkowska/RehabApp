package pwr.ib.rehabapp.home.patient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_patient_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet

class HomePatientFragment : Fragment(), OnExerciseSetItemLongClick {

    private var auth = FirebaseAuth.getInstance()
    private val adapter = ExerciseSetAdapter(this)
    private val homeVm: HomePatientViewModel by activityViewModels()
    private val HOME_DEBUG = "HOME_DEBUG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_patient_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_home.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_home.adapter = adapter
        bindData()
        imageRefresh.setOnClickListener{ bindData() }
        buttonAddNewSet.setOnClickListener{ addNewSets() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onExerciseSetLongClick(exerciseSet: ExerciseSet, position: Int) {
        //homeVm.addExerciseSet(exerciseSet)
        val action = HomePatientFragmentDirections.actionHomePatientFragmentToExerciseListFragment2(
            exerciseSet
        )
        findNavController().navigate(action)
    }

    private fun addNewSets(){
        val action = HomePatientFragmentDirections
            .actionHomePatientFragmentToAddExerciseSetFragment()
        findNavController().navigate(action)
        bindData()
    }

    private fun bindData(){
        Log.d(HOME_DEBUG, "binding data")
        auth = FirebaseAuth.getInstance()
        homeVm.favSets().observe(viewLifecycleOwner) { list ->
            Log.d(HOME_DEBUG, "fav sets list: $list")
            adapter.setExerciseSets(list)
        }

    }


}