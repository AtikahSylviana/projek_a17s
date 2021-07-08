package com.sylvi.penggajiankotlin.activity.kerja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.Bagian_Kerja
import com.sylvi.penggajiankotlin.data.ResponseTambahEditHapusKerja
import com.sylvi.penggajiankotlin.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Tambah_Kerja : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Tambah Bagian Kerja")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_tambah__kerja, container, false)
        val btnTambah: Button = view.findViewById(R.id.btnSimpanTambahkerja)
        val inputNama: EditText = view.findViewById(R.id.inputNamakerja)
        val inputUpah: EditText = view.findViewById(R.id.inputUpahkerja)

        btnTambah.setOnClickListener {
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
            ApiService.instance.tambahKerja(nama, upah.toInt()).enqueue(object :
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