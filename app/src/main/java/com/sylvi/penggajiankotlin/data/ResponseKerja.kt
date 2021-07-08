package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class ResponseKerja (
    @SerializedName("pesan") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val data : List<DataKerja>
    )