package com.sylvi.penggajiankotlin.activity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.kerja.Tambah_Kerja
import com.sylvi.penggajiankotlin.adapter.BagiankerjaAdapter
import com.sylvi.penggajiankotlin.adapter.KaryawanAdapter
import com.sylvi.penggajiankotlin.data.ResponseKaryawan
import com.sylvi.penggajiankotlin.data.ResponseKerja
import com.sylvi.penggajiankotlin.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Karyawan : Fragment() {
    private lateinit var karyawanAdapter: KaryawanAdapter
    private lateinit var listkaryawan: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Karyawan")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_karyawan, container, false)
        var listkaryawan = view.findViewById<RecyclerView>(R.id.listkaryawan)
        karyawanAdapter = KaryawanAdapter(arrayListOf())
        listkaryawan.adapter =karyawanAdapter
        return view
    }

    override fun onStart(){
        super.onStart()
        getKaryawan()
    }


    private fun getKaryawan(){
        ApiService.instance.Karyawan().enqueue(object : Callback<ResponseKaryawan> {
            override fun onResponse(call: Call<ResponseKaryawan>, response: Response<ResponseKaryawan>) {
                if (response.isSuccessful) {

//                    Toast.makeText(getActivity(), response.body()?.message, Toast.LENGTH_LONG).show()
                    val listdata = response.body()!!.data
//                    listdata.forEach {
//                        Log.e("kerja", "nama ${it.nama}")
//                    }
                    karyawanAdapter.setData(listdata)
                }
            }

            override fun onFailure(call: Call<ResponseKaryawan>, t: Throwable) {
                Toast.makeText(getActivity(), t.message, Toast.LENGTH_LONG).show()
//                Log.e("kerja", t.toString())
            }
//
        })
    }
}