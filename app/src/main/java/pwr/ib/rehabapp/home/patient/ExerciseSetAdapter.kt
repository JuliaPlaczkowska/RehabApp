package pwr.ib.rehabapp.home.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.ExerciseSet

class ExerciseSetAdapter(private val listener: OnExerciseSetItemLongClick) :
    RecyclerView.Adapter<ExerciseSetAdapter.ExerciseSetViewHolder>() {

    private val exerciseSetList = ArrayList<ExerciseSet>()


    fun setExerciseSets(list: List<ExerciseSet>) {
        exerciseSetList.clear()
        exerciseSetList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_sets, parent, false)
        return ExerciseSetViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseSetViewHolder, position: Int) {

        bindData(holder)
    }

    private fun bindData(holder: ExerciseSetViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.exerciseSetName)
        //val injurylevel = holder.itemView.findViewById<TextView>(R.id.exerciseSetinjuryLevel)


        name.text = exerciseSetList[holder.adapterPosition].name
        //injurylevel.text = exerciseSetList[holder.adapterPosition].injury_level
    }


    override fun getItemCount(): Int {
        return exerciseSetList.size
    }

    inner class ExerciseSetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {
                listener.onExerciseSetLongClick(exerciseSetList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}

interface OnExerciseSetItemLongClick {
    fun onExerciseSetLongClick(exerciseSet: ExerciseSet, position: Int)
}