package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class DataLogin (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String
)