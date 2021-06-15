package pwr.ib.rehabapp.home.patient

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExerciseSet
import pwr.ib.rehabapp.data.Workout
import java.time.LocalDateTime

class WorkoutAdapter(private val listener: OnShowDetailsClick)
    : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>(){

    private val workoutsList = ArrayList<Workout>()


    fun setWorkouts(list: List<Workout>) {
        workoutsList.clear()
        workoutsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_workouts, parent, false)
        return WorkoutViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        bindData(holder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(holder: WorkoutAdapter.WorkoutViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.setName)
        val tvDate = holder.itemView.findViewById<TextView>(R.id.textDate)
        val tvTime = holder.itemView.findViewById<TextView>(R.id.textTime)

        val workout = workoutsList[holder.adapterPosition];

        name.text = workout.name

        val startDate : LocalDateTime = LocalDateTime.parse(workout.start_date)
        val endDate : LocalDateTime = LocalDateTime.parse(workout.end_date)

        val dateText = (startDate.dayOfMonth.toString()+" "
                +startDate.month
                +" "+startDate.year)
        tvDate.text = dateText
        val timeText = ((endDate.minute - startDate.minute).toString() +" min "
                +(endDate.second - startDate.second).toString()+" sec")
        tvTime.text = timeText

    }

    override fun getItemCount(): Int {
        return workoutsList.size
    }



    inner class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                listener.onShowDetailsClick(workoutsList[adapterPosition], adapterPosition)
                true
            }
        }
    }


}

interface OnShowDetailsClick {
    fun onShowDetailsClick(workout: Workout, position: Int)
}