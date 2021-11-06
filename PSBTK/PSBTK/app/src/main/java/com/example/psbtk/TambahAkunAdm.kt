package com.example.psbtk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.psbtk.databinding.ActivityTambahAkunAdmBinding
import org.json.JSONObject

class TambahAkunAdm : AppCompatActivity() {
    private lateinit var binding: ActivityTambahAkunAdmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahAkunAdmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btTmbhakun.setOnClickListener {


            val username = binding.etNewuname.text.toString()
            val pwd = binding.etNewpass.text.toString()

            if (username.isEmpty()) {
                binding.etNewuname.error = "Kosong"
                binding.etNewuname.requestFocus()
            } else if (pwd.isEmpty()) {
                binding.etNewpass.error = "Kosong"
                binding.etNewpass.requestFocus()
            } else {
                //simpan data
                AndroidNetworking.post("http://192.168.0.108/apk_daftar/tambahakun.php")
                    .addBodyParameter("username", username)
                    .addBodyParameter("pwd", pwd)
                    .setPriority(com.androidnetworking.common.Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(
                                    this@TambahAkunAdm, response.getString("pesan"),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@TambahAkunAdm, response.getString("pesan"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onError(error: ANError) {
                            Toast.makeText(this@TambahAkunAdm, error.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    })

            }
        }
    }
}