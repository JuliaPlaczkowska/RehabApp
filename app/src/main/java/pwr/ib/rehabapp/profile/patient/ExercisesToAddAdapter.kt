package pwr.ib.rehabapp.home.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_row_add_exercise.view.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.doctor.OnPatientItemLongClick

class ExercisesToAddAdapter (private val listener: OnExerciseItemLongClick) :
    RecyclerView.Adapter<ExercisesToAddAdapter.ExercisesToAddViewHolder>(){

    private val exercisesList = ArrayList<Exercise>()
    private val exerciseSelected = ArrayList<Exercise>()


    fun setExercises(list: List<Exercise>) {
        exercisesList.clear()
        exercisesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesToAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_add_exercise, parent, false)
        return ExercisesToAddViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercisesToAddViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: ExercisesToAddAdapter.ExercisesToAddViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.exerciseName)
        val injurylevel = holder.itemView.findViewById<TextView>(R.id.exerciseInjuryLevel)
        val reps = holder.itemView.findViewById<TextView>(R.id.exerciseReps)
        val image = holder.itemView.findViewById<ImageView>(R.id.exerciseImage)

        name.text = exercisesList[holder.adapterPosition].name
        reps.text = exercisesList[holder.adapterPosition].reps

    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    inner class ExercisesToAddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {

                val checkBox : CheckBox = this.itemView.findViewById<CheckBox>(R.id.cb)

                if(exerciseSelected.contains(exercisesList[adapterPosition])) {
                    exerciseSelected.remove(exercisesList[adapterPosition])
                    checkBox.isChecked = false
                }
                else {
                    exerciseSelected.add(exercisesList[adapterPosition])
                    checkBox.isChecked = true
                }


                listener.onExerciseLongClick(exercisesList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}

interface OnExerciseItemLongClick {
    fun onExerciseLongClick(exercise: Exercise, position: Int)
}