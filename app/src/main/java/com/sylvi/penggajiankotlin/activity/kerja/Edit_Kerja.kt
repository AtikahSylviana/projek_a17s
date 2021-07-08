package com.sylvi.penggajiankotlin.activity.kerja

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.Bagian_Kerja
import com.sylvi.penggajiankotlin.data.ResponseKerja
import com.sylvi.penggajiankotlin.data.ResponseTambahEditHapusKerja
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Edit_Kerja : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Toast.makeText(requireActivity(), id, Toast.LENGTH_LONG).show()
//        Log.e("kerja", id.toString())
        getActivity()?.setTitle("Edit Bagian Kerja")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var id = SharedPrefManager.getInstance(requireContext()).id
        var view = inflater.inflate(R.layout.fragment_edit__kerja, container, false)
        val btnSimpan: Button = view.findViewById(R.id.btnSimpanEditkerja)
        val inputNama: EditText = view.findViewById(R.id.inputNamakerja)
        val inputUpah: EditText = view.findViewById(R.id.inputUpahkerja)
        setDataBagianKerja(id,inputNama, inputUpah)
        btnSimpan.setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val upah = inputUpah.text.toString().trim()

            if(nama.isEmpty()){
                inputNama.error = "Isi Nama Jenis Kerja"
                inputNama.requestFocus()
                return@setOnClickListener
            }
            if(upah.isEmpty()) {
                inputUpah.error = "Isi Jumlah Upah"
                inputUpah.requestFocus()
                return@setOnClickListener
            }
            ApiService.instance.EditKerja(id, nama, upah.toInt()).enqueue(object :
                    Callback<ResponseTambahEditHapusKerja> {

                override fun onFailure(call: Call<ResponseTambahEditHapusKerja>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                        call: Call<ResponseTambahEditHapusKerja>,
                        responseEditHapus: Response<ResponseTambahEditHapusKerja>
                ) {
                    if (responseEditHapus.body()?.status!!) {
                        kembaliBagianKerja()
                        Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        return view
     }
    private fun setDataBagianKerja(id: Int,Nama: EditText, Upah: EditText) {
        ApiService.instance.formEditKerja(id).enqueue(object : Callback<ResponseKerja> {
            override fun onFailure(call: Call<ResponseKerja>, t: Throwable) {
                Toast.makeText(getActivity(), t.message, Toast.LENGTH_LONG).show()
                Log.e("kerja", t.toString())
            }

            override fun onResponse(call: Call<ResponseKerja>, response: Response<ResponseKerja>) {
                if (response.isSuccessful) {

//                    Toast.makeText(getActivity(), response.body()?.message, Toast.LENGTH_LONG).show()
                    val listdata = response.body()!!.data
                    listdata.forEach {
                        Nama.setText("${it.nama}")
                        Upah.setText("${it.updah}")
//                        Log.e("kerja", "nama ${it.nama}")
//                        Log.e("kerja", "upah ${it.updah}")
                    }

                }
            }
        })
    }

    fun kembaliBagianKerja(){
        val fragment: Fragment = Bagian_Kerja()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fLayout, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }

}