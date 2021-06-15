package pwr.ib.rehabapp.profile.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_row_add_exercise.view.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.User
import pwr.ib.rehabapp.home.doctor.OnPatientItemLongClick

class ExercisesToAddAdapter (private val listener: OnExerciseItemLongClick) :
    RecyclerView.Adapter<ExercisesToAddAdapter.ExercisesToAddViewHolder>(){

    private val exercisesList = ArrayList<Exercise>()
    val exerciseSelected = ArrayList<String>()


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
       
        name.text = exercisesList[holder.adapterPosition].name
        reps.text = exercisesList[holder.adapterPosition].reps
        injurylevel.text = "injury level ${exercisesList[holder.adapterPosition].injury_level}"

        val image = holder.itemView.findViewById<ImageView>(R.id.exerciseImage)
        Glide.with(holder.itemView)
            .load(exercisesList[holder.adapterPosition].image)
            .into(image)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    inner class ExercisesToAddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {

                if(exerciseSelected.contains(exercisesList[adapterPosition].eid)) {
                    exerciseSelected.remove(exercisesList[adapterPosition].eid.toString())
//                    val added : ImageView = this.itemView.findViewById<ImageView>(R.id.imageAdded)
//                    added.visibility = View.INVISIBLE
                    Snackbar.make(
                        view,
                        "${exercisesList[adapterPosition].name} removed",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else {
                    exerciseSelected.add(exercisesList[adapterPosition].eid.toString())
                    Snackbar.make(
                        view,
                        "${exercisesList[adapterPosition].name} added",
                        Snackbar.LENGTH_SHORT
                    ).show()
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