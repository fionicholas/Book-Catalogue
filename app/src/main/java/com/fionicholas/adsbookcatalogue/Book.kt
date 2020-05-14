package com.fionicholas.adsbookcatalogue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book (
    val id : String,
    val title : String,
    var rating : Double,
    var publisher : String,
    var description : String
) : Parcelable