package pwr.ib.rehabapp.home.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.patient.HomePatientFragmentDirections

class PatientsAdapter(private val listener: OnPatientItemLongClick) :
    RecyclerView.Adapter<PatientsAdapter.PatientsViewHolder>() {

    private val patientsList = ArrayList<User>()


    fun setPatients(list: List<User>) {
        patientsList.clear()
        patientsList.addAll(list)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_patients, parent, false)
        return PatientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientsViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: PatientsViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.patientName)
        val injury = holder.itemView.findViewById<TextView>(R.id.tvInjuries)

        name.text = patientsList[holder.adapterPosition].surname

        var injuries = patientsList[holder.adapterPosition].injuries

        if (injuries.isNullOrEmpty())
            injury.text = "no injuries"
        else
            injury.text =
                when {
                    injuries!!.size > 2 -> (injuries[0] + ", " + injuries[2] + ", ...")
                    injuries!!.size == 2 -> (injuries[0] + ", " + injuries[2])
                    injuries!!.size == 1 -> (injuries[0])
                    else -> "no injuries"
                }

    }


    override fun getItemCount(): Int {
        return patientsList.size
    }

    inner class PatientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {
                listener.onPatientLongClick(patientsList[adapterPosition], adapterPosition)
                true
            }
        }
    }


}

interface OnPatientItemLongClick {
    fun onPatientLongClick(user: User, position: Int)
}
