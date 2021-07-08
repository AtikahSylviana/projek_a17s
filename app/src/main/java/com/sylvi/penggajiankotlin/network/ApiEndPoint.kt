package com.sylvi.penggajiankotlin.network

import com.google.gson.annotations.SerializedName
import com.sylvi.penggajiankotlin.data.*
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {
    @FormUrlEncoded
    @POST("login")
    fun Login(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<ResponseLogin>
    @GET("kerja")
    fun Kerja(): Call<ResponseKerja>
    @FormUrlEncoded
    @POST("kerja/store")
    fun tambahKerja(
        @Field("nama") nama:String,
        @Field("upah") upah:Int
    ): Call<ResponseTambahEditHapusKerja>
    @GET("kerja/edit/{id}")
    fun formEditKerja(
            @Path("id") id:Int
    ): Call<ResponseKerja>
    @FormUrlEncoded
    @PUT("kerja/update/{id}")
    fun EditKerja(
            @Path("id") id:Int,
            @Field("nama") nama:String,
            @Field("upah") upah:Int
    ): Call<ResponseTambahEditHapusKerja>
    @DELETE("kerja/delete/{id}")
    fun hapusKerja(
            @Path("id") id:Int
    ): Call<ResponseTambahEditHapusKerja>
    @GET("karyawan")
    fun Karyawan(): Call<ResponseKaryawan>
    @GET("karyawan/edit/{id}")
    fun formEditDetailKerja(
            @Path("id") id:Int
    ): Call<ResponseKaryawan>
    @FormUrlEncoded
    @PUT("karyawan/update/{id}")
    fun EditKaryawan(
            @Path("id") id:Int,
            @Field("nama") nama:String,
            @Field("alamat") alamat : String,
            @Field("whatsapp") whatsapp : String,
            @Field("bagian_kerja")  bagian_kerja : Int
    ): Call<ResponseEditHapusKaryawan>
    @DELETE("karyawan/delete/{id}")
    fun hapusKaryawan(
            @Path("id") id:Int
    ): Call<ResponseEditHapusKaryawan>
}