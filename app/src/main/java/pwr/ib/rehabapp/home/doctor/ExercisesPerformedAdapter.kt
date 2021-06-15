package pwr.ib.rehabapp.home.doctor

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.set_finished_fragment.*
import pwr.ib.rehabapp.R
import pwr.ib.rehabapp.data.Exercise
import pwr.ib.rehabapp.data.ExercisePerformed
import pwr.ib.rehabapp.home.patient.HomePatientViewModel
import pwr.ib.rehabapp.home.patient.OnExerciseSetItemLongClick
import java.time.LocalDateTime

class ExercisesPerformedAdapter() : RecyclerView.Adapter<ExercisesPerformedAdapter.EpViewHolder>() {

    private val epList = ArrayList<ExercisePerformed>()
    private val eList = ArrayList<Exercise>()


    fun setEp(list: List<ExercisePerformed>) {
        epList.clear()
        epList.addAll(list)
        notifyDataSetChanged()
    }

//    fun setE(list: List<Exercise>) {
//        eList.clear()
//        eList.addAll(list)
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row_exercise_performed, parent, false)
        return EpViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EpViewHolder, position: Int) {
        if(!epList.isNullOrEmpty())
        bindData(holder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(holder: EpViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.exerciseName)
        val injury = holder.itemView.findViewById<TextView>(R.id.exerciseInjuryLevel)
        val reps = holder.itemView.findViewById<TextView>(R.id.exerciseReps)
        val time = holder.itemView.findViewById<TextView>(R.id.textTime2)
        val skipped = holder.itemView.findViewById<TextView>(R.id.textSkipped)
        val imgUp = holder.itemView.findViewById<ImageView>(R.id.imageThumbUp)
        val imgDown = holder.itemView.findViewById<ImageView>(R.id.imageThumbDown)
        val desc = holder.itemView.findViewById<TextView>(R.id.textDescription)
        val image = holder.itemView.findViewById<ImageView>(R.id.exerciseImage)

        Glide.with(holder.itemView)
            .load(epList[holder.adapterPosition].image)
            .into(image)

        name.text = epList[holder.adapterPosition].name
        injury.text = epList[holder.adapterPosition].injury_level
        reps.text = epList[holder.adapterPosition].reps

        val startDate : LocalDateTime = LocalDateTime.parse(epList[holder.adapterPosition].start_date)
        val endDate : LocalDateTime = LocalDateTime.parse(epList[holder.adapterPosition].end_date)

        var min = endDate.minute - startDate.minute
        var sec = endDate.second - startDate.second

        if(sec < 0){
            min -= min
            sec += 60
        }
        time.text = "$min min $sec sec"

        skipped.text = "skipped: ${ epList[holder.adapterPosition].skipped.toString() }"

        if(epList[holder.adapterPosition].feedback=="good") {
            imgUp.visibility = View.VISIBLE
            imgDown.visibility = View.INVISIBLE
        }
        else if (epList[holder.adapterPosition].feedback=="bad") {
            imgDown.visibility = View.VISIBLE
            imgUp.visibility = View.INVISIBLE
        }
        else{
            imgDown.visibility = View.INVISIBLE
            imgUp.visibility = View.INVISIBLE
        }

        desc.text = epList[holder.adapterPosition].description
    }

    override fun getItemCount(): Int {
        return epList.size
    }

    inner class EpViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
        }
    }
}

