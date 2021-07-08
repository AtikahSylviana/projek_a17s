package com.sylvi.penggajiankotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sylvi.penggajiankotlin.R
import com.sylvi.penggajiankotlin.activity.pengaturan.Ganti_Password
import com.sylvi.penggajiankotlin.storage.SharedPrefManager


class Pengaturan : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getActivity()?.setTitle("Pengaturan")
        val view: View = inflater!!.inflate(R.layout.fragment_pengaturan, container, false)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnGantipwd: Button = view.findViewById(R.id.btnGantipwd)
        btnLogout.setOnClickListener { logout() }
        btnGantipwd.setOnClickListener { gantiPwd() }
        return view
    }
    fun gantiPwd(){
        val fragment: Fragment = Ganti_Password()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fLayout, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }
    fun logout() {
        SharedPrefManager.getInstance(requireActivity()).clear()
        val intent = Intent(getActivity(), LoginActivity::class.java)
        getActivity()?.startActivity(intent)
        getActivity()?.finish()

    }

}