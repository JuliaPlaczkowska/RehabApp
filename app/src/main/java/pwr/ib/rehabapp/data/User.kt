package pwr.ib.rehabapp.data

data class User(
    val uid: String? = null,
    val role: String? = null,
    val surname: String? = null,
    val height: Float? = null,
    val weight: Float? = null,
    val exercise_set: List<String>? = null,
    val injuries: List<String>? = null
)