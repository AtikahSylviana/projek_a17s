package com.sylvi.penggajiankotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.Bagian_Kerja
import com.sylvi.penggajiankotlin.activity.kerja.Edit_Kerja
import com.sylvi.penggajiankotlin.data.DataKerja
import com.sylvi.penggajiankotlin.data.ResponseTambahEditHapusKerja
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BagiankerjaAdapter(val kerja: ArrayList<DataKerja>):RecyclerView.Adapter<BagiankerjaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_bagian_kerja, parent, false)
    )

    override fun onBindViewHolder(holder: BagiankerjaAdapter.ViewHolder, position: Int) {
        val data = kerja[position]
        holder.txtNamaKrja.text = data.nama
        val idData = data.id
        holder.cardDatakerja.setOnClickListener {
            editKerja(holder.cardDatakerja, idData)
        }
        holder.btnHapus.setOnClickListener {
            hapusKerja(holder.btnHapus, data.nama, idData)
        }
    }

    private fun hapusKerja(view: View, Nama: String, idData: Int) {
        val activity = view.context as AppCompatActivity
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Hapus Data")
        alertDialogBuilder.setMessage("Apakah Yakin Menghapus Data ${Nama} ?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        alertDialogBuilder.setPositiveButton("Ya") { dialog, which ->
            hapusData(view, idData)
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialog, which ->
            dialog.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun hapusData(view: View,id: Int) {
        val activity = view.context as AppCompatActivity

        ApiService.instance.hapusKerja(id).enqueue(object : Callback<ResponseTambahEditHapusKerja> {

            override fun onFailure(call: Call<ResponseTambahEditHapusKerja>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<ResponseTambahEditHapusKerja>,
                    responseEditHapus: Response<ResponseTambahEditHapusKerja>
            ) {
                if (responseEditHapus.body()?.status!!) {
                    kembaliBagianKerja(view)
                    Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, responseEditHapus.body()?.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun editKerja(view: View, idData: Int) {

        val activity = view.context as AppCompatActivity
        SharedPrefManager.getInstance(activity).saveId(idData)
        val myFragment: Fragment = Edit_Kerja()
        activity.supportFragmentManager.beginTransaction().replace(R.id.fLayout, myFragment).addToBackStack(null).commit()

    }
    override fun getItemCount()=kerja.size
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val  txtNamaKrja = view.findViewById<TextView>(R.id.txtNamakerja)
        val  btnHapus =  view.findViewById<ImageView>(R.id.btnHapuskerja)
        val cardDatakerja = view.findViewById<MaterialCardView>(R.id.dataKerja)
    }

    public fun setData(data: List<DataKerja>){
        kerja.clear()
        kerja.addAll(data)
        notifyDataSetChanged()
    }
    fun kembaliBagianKerja(view: View){
        val activity = view.context as AppCompatActivity
        val fragment: Fragment = Bagian_Kerja()
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        fragmentManager.popBackStack()
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fLayout, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }
}