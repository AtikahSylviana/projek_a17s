package com.sylvi.penggajiankotlin.data

import com.google.gson.annotations.SerializedName

class DataKaryawan (
    @SerializedName("id") val id : Int,
    @SerializedName("sidik_jari") val sidik_jari : String,
    @SerializedName("nama") val nama : String,
    @SerializedName("alamat") val alamat : String,
    @SerializedName("whatsapp") val whatsapp : String,
    @SerializedName("bagian_kerja") val bagian_kerja : Int
)