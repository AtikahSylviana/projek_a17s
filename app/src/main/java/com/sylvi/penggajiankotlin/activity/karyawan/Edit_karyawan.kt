package com.sylvi.penggajiankotlin.activity.karyawan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.Bagian_Kerja
import com.sylvi.penggajiankotlin.data.ResponseEditHapusKaryawan
import com.sylvi.penggajiankotlin.data.ResponseKaryawan
import com.sylvi.penggajiankotlin.data.ResponseTambahEditHapusKerja
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Edit_karyawan : Fragment() {

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Edit Data Karyawan")
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_karyawan, container, false)
        var id = SharedPrefManager.getInstance(requireContext()).id
        val edtNama: EditText = view.findViewById(R.id.inputNamaKaryawan)
        val edtAlamat: EditText = view.findViewById(R.id.inputAlamatKaryawan)
        val edtWA: EditText = view.findViewById(R.id.inputWAKaryawan)
        val edtBagianKerja: EditText = view.findViewById(R.id.inputBagianKerjaKaryawan)
        val btnSimpan : Button = view.findViewById(R.id.btnSimpanEditKaryawan)
        setDataKaryawan(id,edtNama, edtAlamat,edtWA, edtBagianKerja)
        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString().trim()
            val alamat = edtAlamat.text.toString().trim()
            val whatsapp = edtWA.text.toString().trim()
            val bagiankerja = edtBagianKerja.text.toString().trim()
            if(nama.isEmpty()){
                edtNama.error = "Isi Nama Karyawan"
                edtNama.requestFocus()
                return@setOnClickListener
            }
            if(alamat.isEmpty()) {
                edtAlamat.error = "Isi Alamat Karyawan"
                edtAlamat.requestFocus()
                return@setOnClickListener
            }
            if(whatsapp.isEmpty()) {
                edtWA.error = "Isi Whatsapp Karyawan"
                edtWA.requestFocus()
                return@setOnClickListener
            }
            if(bagiankerja == "0") {
                edtAlamat.error = "Isi Alamat Karyawan"
                edtAlamat.requestFocus()
                return@setOnClickListener
            }
            ApiService.instance.EditKaryawan(id,nama, alamat ,whatsapp, bagiankerja.toInt()).enqueue(object :
                Callback<ResponseEditHapusKaryawan> {

                override fun onFailure(call: Call<ResponseEditHapusKaryawan>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseEditHapusKaryawan>,
                    responseEditHapus: Response<ResponseEditHapusKaryawan>
                ) {
                    if (responseEditHapus.body()?.status!!) {
                        kembaliDetailKaryawan()
                        Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        return view
    }
    private fun setDataKaryawan(id: Int,Nama: EditText, Alamat: EditText,WA: EditText, BagianKerja: EditText) {
        ApiService.instance.formEditDetailKerja(id).enqueue(object : Callback<ResponseKaryawan> {

            override fun onFailure(call: Call<ResponseKaryawan>, t: Throwable) {
                Toast.makeText(getActivity(), t.message, Toast.LENGTH_LONG).show()
                Log.e("kerja", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseKaryawan>,
                response: Response<ResponseKaryawan>
            ) {
                if (response.isSuccessful) {

//                    Toast.makeText(getActivity(), response.body()?.message, Toast.LENGTH_LONG).show()
                    val listdata = response.body()!!.data
                    var dataAlamat: String? =null
                    var dataWA: String? =null
                    var dataBagiankerja:Int =0
                    listdata.forEach {
                        Nama.setText("${it.nama}")
                        dataAlamat =  it.alamat
                        dataWA =  it.whatsapp
                        dataBagiankerja = it.bagian_kerja
//                        Log.e("kerja", "nama ${it.nama}")
//                        Log.e("kerja", "upah ${it.updah}")
                    }
                    if (dataAlamat == null) Alamat.setText("")
                    else Alamat.setText(dataAlamat)
                    if (dataWA == null) WA.setText("")
                    else WA.setText(dataWA)
                    if (dataBagiankerja == 0) BagianKerja.setText("")
                    else BagianKerja.setText("$dataBagiankerja")
                }
            }

        })
    }
    fun kembaliDetailKaryawan(){
        val fragment: Fragment = DetailKaryawan()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fLayout, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }
}