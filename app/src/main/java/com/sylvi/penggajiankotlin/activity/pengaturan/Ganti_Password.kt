package com.sylvi.penggajiankotlin.activity.pengaturan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.MainActivity
import com.sylvi.penggajiankotlin.data.ResponseLogin
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_ganti__password.*
import kotlinx.android.synthetic.main.fragment_pengaturan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ganti_Password : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Ganti Password")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_ganti__password, container, false)
        val btnGantipwd: Button = view.findViewById(R.id.btnGantipwd)
        val inputPwdLama: EditText = view.findViewById(R.id.inputPwdLama)
        val inputPwdBaru: EditText = view.findViewById(R.id.inputPwdBaru)
        val inputPwdUlang: EditText = view.findViewById(R.id.inputPwdUlang)

        btnGantipwd.setOnClickListener {
            val lama = inputPwdLama.text.toString().trim()
            val baru = inputPwdBaru.text.toString().trim()
            val ulang = inputPwdUlang.text.toString().trim()

            if(lama.isEmpty()){
                inputPwdLama.error = "Isi Password Lama"
                inputPwdLama.requestFocus()
                return@setOnClickListener
            }
            if(baru.isEmpty()){
                inputPwdBaru.error = "Isi Password Baru"
                inputPwdBaru.requestFocus()
                return@setOnClickListener
            }
            if(ulang.isEmpty()){
                inputPwdUlang.error = "Ulamgi Password Lama"
                inputPwdUlang.requestFocus()
                return@setOnClickListener
            }

//            ApiService.instance.Login(email, password).enqueue(object: Callback<ResponseLogin> {
//                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                }
//
//                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                    if(response.body()?.status!!){
//
//                        SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.data!!)
//
//                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
//                        val intent = Intent(applicationContext, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//                    }else{
//                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
//                    }
//
//                }
//            })

        }
        return  view
    }

}