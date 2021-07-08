package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class ResponseEditHapusKaryawan (
    @SerializedName("pesan") val message : String,
    @SerializedName("status") val status : Boolean
    )