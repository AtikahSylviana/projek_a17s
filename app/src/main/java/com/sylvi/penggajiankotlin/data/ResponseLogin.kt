package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class ResponseLogin (
    @SerializedName("pesan") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("dataLogin") val data : DataLogin
    )