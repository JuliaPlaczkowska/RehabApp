package pwr.ib.rehabapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String? = null,
    var did: String? = null,
    var role: String? = null,
    var surname: String? = null,
    var height: Float? = null,
    var weight: Float? = null,
    var exercise_set: List<String>? = null,
    var injuries: List<String>? = null
) : Parcelable

