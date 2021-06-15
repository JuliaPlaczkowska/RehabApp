package pwr.ib.rehabapp.home.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet

class ExercisesAdapter :RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder>(){

    private val exercisesList = ArrayList<Exercise>()


    fun setExercises(list: List<Exercise>) {
        exercisesList.clear()
        exercisesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_exercises, parent, false)
        return ExercisesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: ExercisesAdapter.ExercisesViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.exerciseName)
        val injurylevel = holder.itemView.findViewById<TextView>(R.id.exerciseInjuryLevel)
        val reps = holder.itemView.findViewById<TextView>(R.id.exerciseReps)


        name.text = exercisesList[holder.adapterPosition].name
        injurylevel.text =  "injury level ${exercisesList[holder.adapterPosition].injury_level}"
        reps.text = exercisesList[holder.adapterPosition].reps

        val image = holder.itemView.findViewById<ImageView>(R.id.exerciseImage)
        Glide.with(holder.itemView)
            .load(exercisesList[holder.adapterPosition].image)
            .into(image)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    inner class ExercisesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init { }
    }
}