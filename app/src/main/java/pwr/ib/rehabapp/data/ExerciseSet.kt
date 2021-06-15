package pwr.ib.rehabapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ExerciseSet(
    var sid: String? = null,
    var name: String? = null,
    var exercises: List<String>? = null,
    var injury_level: String? = null
) : Parcelable