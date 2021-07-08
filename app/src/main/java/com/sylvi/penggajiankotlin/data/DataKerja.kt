package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class DataKerja (
    @SerializedName("id") val id : Int,
    @SerializedName("nama") val nama : String,
    @SerializedName("upah") val updah : Int
)