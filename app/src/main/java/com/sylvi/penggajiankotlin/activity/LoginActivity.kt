package com.sylvi.penggajiankotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.data.ResponseLogin
import com.sylvi.penggajiankotlin.network.ApiService
import com.sylvi.penggajiankotlin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnmasuk.setOnClickListener {

            val email = inputemail.text.toString().trim()
            val password = inputpassword.text.toString().trim()

            if(email.isEmpty()){
                inputemail.error = "Email required"
                inputemail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                inputpassword.error = "Password required"
                inputpassword.requestFocus()
                return@setOnClickListener
            }

            ApiService.instance.Login(email, password).enqueue(object: Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if(response.body()?.status!!){

                        SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.data!!)

                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }

                }
            })

        }

    }
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}