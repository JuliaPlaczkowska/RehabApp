package pwr.ib.rehabapp.data

data class ExercisePerformed(
    var eid: String? = null,
    var start_date: String? = null,
    var end_date: String? = null,
    var feedback: String? = null,
    var skipped: Boolean? = false,
    var epid: String? = null
    ,

    var category: String? = null,
    var name: String? = null,
    var reps: String? = null,
    var image: String? = null,
    var description: String? = null,
    var back_part: String? = null,
    var injury_level: String? = null
)

