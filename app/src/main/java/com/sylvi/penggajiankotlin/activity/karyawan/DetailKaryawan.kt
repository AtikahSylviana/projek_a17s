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
import androidx.appcompat.app.AppCompatActivity
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.data.ResponseKaryawan
import com.sylvi.penggajiankotlin.data.ResponseKerja
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailKaryawan : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Detail Karyawan")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_karyawan, container, false)
        var id = SharedPrefManager.getInstance(requireContext()).id
        val txtNama: TextView = view.findViewById(R.id.txtNamakaryawan)
        val txtAlamat: TextView = view.findViewById(R.id.txtAlamatkaryawan)
        val txtWA: TextView = view.findViewById(R.id.txtwhatsappkaryawan)
        val txtBagianKerja: TextView = view.findViewById(R.id.txtbagiankerjakaryawan)
        val btnEdit: Button =view.findViewById(R.id.btnEditKaryawan)
        btnEdit.setOnClickListener{editKaryawan(btnEdit, id)}
        setDataKaryawan(id,txtNama, txtAlamat,txtWA, txtBagianKerja)
        return view
    }

    private fun setDataKaryawan(id: Int,Nama: TextView, Alamat: TextView,WA: TextView, BagianKerja: TextView) {
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
                    if (dataAlamat == null) Alamat.setText("Data Belum Diatur")
                    else Alamat.setText(dataAlamat)
                    if (dataWA == null) WA.setText("Data Belum Diatur")
                    else WA.setText(dataWA)
                    if (dataBagiankerja == 0) BagianKerja.setText("Data Belum Diatur")
                    else setNamaBagianKerja(dataBagiankerja, BagianKerja)
                }
            }

        })
    }
    private fun editKaryawan(view: View, idData: Int) {
        val activity = view.context as AppCompatActivity
        SharedPrefManager.getInstance(activity).saveId(idData)
        val myFragment: Fragment = Edit_karyawan()
        activity.supportFragmentManager.beginTransaction().replace(R.id.fLayout, myFragment).addToBackStack(null).commit()
    }
    private fun setNamaBagianKerja(id: Int,bagiankerja: TextView) {
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
                        bagiankerja.setText("${it.nama}")
//                        Log.e("kerja", "nama ${it.nama}")
//                        Log.e("kerja", "upah ${it.updah}")
                    }

                }
            }
        })
    }
}