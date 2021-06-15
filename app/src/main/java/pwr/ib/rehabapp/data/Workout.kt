package pwr.ib.rehabapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
@Parcelize
data class Workout (
    var sid: String? = null,
    var uid: String? = null,
    var name: String? = null,
    var start_date: String? = null,
    var exercises: List<String>? = null,
    var end_date: String? = null,

) : Parcelable