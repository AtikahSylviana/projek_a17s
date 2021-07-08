package com.sylvi.penggajiankotlin.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.kerja.Tambah_Kerja
import com.sylvi.penggajiankotlin.adapter.BagiankerjaAdapter
import com.sylvi.penggajiankotlin.data.ResponseKerja
import com.sylvi.penggajiankotlin.network.ApiService
import kotlinx.android.synthetic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Bagian_Kerja : Fragment() {
    private lateinit var kerjaadapter:BagiankerjaAdapter
    private lateinit var listkerjar: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Bagian Kerja")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_bagian__kerja, container, false)
        var listkerja = view.findViewById<RecyclerView>(R.id.listkerja)
        kerjaadapter = BagiankerjaAdapter(arrayListOf())
        listkerja.adapter =kerjaadapter
        val btnTambah: FloatingActionButton = view.findViewById(R.id.btnTmbahkerja)
        btnTambah.setOnClickListener { tambahKerja() }
        return view
    }

    fun tambahKerja() {
        val fragment: Fragment = Tambah_Kerja()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fLayout, fragment)
        fragmentTransaction.addToBackStack("bagian kerja")
        fragmentTransaction.commit()
    }

    override fun onStart(){
        super.onStart()
        getKerja()
    }


    private fun getKerja(){
        ApiService.instance.Kerja().enqueue(object : Callback<ResponseKerja> {
            override fun onFailure(call: Call<ResponseKerja>, t: Throwable) {
                Toast.makeText(getActivity(), t.message, Toast.LENGTH_LONG).show()
                Log.e("kerja", t.toString())
            }

            override fun onResponse(call: Call<ResponseKerja>, response: Response<ResponseKerja>) {
                if (response.isSuccessful) {

//                    Toast.makeText(getActivity(), response.body()?.message, Toast.LENGTH_LONG).show()
                    val listdata = response.body()!!.data
//                    listdata.forEach {
//                        Log.e("kerja", "nama ${it.nama}")
//                    }
                    kerjaadapter.setData(listdata)
                }
            }
        })
    }
}