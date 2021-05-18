package pwr.ib.rehabapp.data

data class ExerciseSet(
    val sid: String? = null,
    val name: String? = null,
    val exercises: List<String>? = null,
    val injury_level: String? = null,
    val image: String? = null
)