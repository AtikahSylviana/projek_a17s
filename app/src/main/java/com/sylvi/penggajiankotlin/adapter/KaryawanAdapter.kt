package com.sylvi.penggajiankotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.karyawan.DetailKaryawan
import com.sylvi.penggajiankotlin.activity.kerja.Edit_Kerja
import com.sylvi.penggajiankotlin.data.DataKaryawan
import com.sylvi.penggajiankotlin.storage.SharedPrefManager


class KaryawanAdapter(val karyawan: ArrayList<DataKaryawan>):RecyclerView.Adapter<KaryawanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_karyawan, parent, false)
    )

    override fun onBindViewHolder(holder: KaryawanAdapter.ViewHolder, position: Int) {
        val data = karyawan[position]
        holder.txtNamaKaryawan.text =data.nama
        val idData = data.id
        holder.cardDatakaryawan.setOnClickListener {
            datailKaryawan(holder.cardDatakaryawan, idData)
        }
        holder.btnHapus.setOnClickListener {
            hapusKaryawan(holder.btnHapus, data.nama, idData)
        }
    }

    private fun datailKaryawan(view: View, idData: Int) {
        val activity = view.context as AppCompatActivity
        SharedPrefManager.getInstance(activity).saveId(idData)
        val myFragment: Fragment = DetailKaryawan()
        activity.supportFragmentManager.beginTransaction().replace(R.id.fLayout, myFragment).addToBackStack(null).commit()
    }

    private fun hapusKaryawan(view: View, Nama: String, idData: Int) {
        val activity = view.context as AppCompatActivity
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Hapus Data")
        alertDialogBuilder.setMessage("Apakah Yakin Menghapus Data ${Nama} ?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        alertDialogBuilder.setPositiveButton("Ya") { dialog, which ->
//            hapusData(view, idData)
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialog, which ->
            dialog.dismiss()
        }

        alertDialogBuilder.show()
    }

//    private fun hapusData(view: View,id: Int) {
//        val activity = view.context as AppCompatActivity
//
//        ApiService.instance.hapusKerja(id).enqueue(object : Callback<ResponseTambahKerja> {
//
//            override fun onFailure(call: Call<ResponseTambahKerja>, t: Throwable) {
//                Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(
//                    call: Call<ResponseTambahKerja>,
//                    response: Response<ResponseTambahKerja>
//            ) {
//                if (response.body()?.status!!) {
//                    kembaliBagianKerja(view)
//                    Toast.makeText(activity, response.body()?.message, Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(activity, response.body()?.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    }

//    fun editKerja(view: View, idData: Int) {
//
//        val activity = view.context as AppCompatActivity
//        SharedPrefManager.getInstance(activity).saveId(idData)
//        val myFragment: Fragment = Edit_Kerja()
//        activity.supportFragmentManager.beginTransaction().replace(R.id.fLayout, myFragment).addToBackStack(null).commit()
//
//    }
    override fun getItemCount()=karyawan.size
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val  txtNamaKaryawan = view.findViewById<TextView>(R.id.txtNamakaryawan)
        val  btnHapus =  view.findViewById<ImageView>(R.id.btnHapuskaryawan)
        val cardDatakaryawan = view.findViewById<MaterialCardView>(R.id.dataKaryawan)
    }

    public fun setData(data: List<DataKaryawan>){
        karyawan.clear()
        karyawan.addAll(data)
        notifyDataSetChanged()
    }
//    fun kembaliBagianKerja(view: View){
//        val activity = view.context as AppCompatActivity
//        val fragment: Fragment = Bagian_Kerja()
//        val fragmentManager: FragmentManager = activity.supportFragmentManager
//        fragmentManager.popBackStack()
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fLayout, fragment)
//        fragmentTransaction.disallowAddToBackStack()
//        fragmentTransaction.commit()
//    }
}