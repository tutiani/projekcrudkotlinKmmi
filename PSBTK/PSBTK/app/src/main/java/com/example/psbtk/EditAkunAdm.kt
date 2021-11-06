package com.example.psbtk

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.example.psbtk.databinding.ActivityEditAkunAdmBinding
import com.example.psbtk.databinding.ActivityLoginAdminBinding
import org.json.JSONObject
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener

class EditAkunAdm : AppCompatActivity() {
    private lateinit var binding: ActivityEditAkunAdmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAkunAdmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSimpan.setOnClickListener {
            Toast.makeText(this@EditAkunAdm, "Berhasil", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@EditAkunAdm, DashboardAdmin::class.java))

            val username = binding.etUname.text.toString()
            val password = binding.etPass.text.toString()
            if (username.isEmpty()){
                binding.etUname.error = "Kosong"
                binding.etUname.requestFocus()
            }else if (password.isEmpty()){
                binding.etPass.error = "Kosong"
                binding.etPass.requestFocus()
            }else{
                update(intent.getStringExtra("id").toString(),username,password.toString())
            }
        }
    }
    fun update(id_login : String, username : String, password : String ){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post("http://192.168.0.108/apk_daftar/login.php")
            .addBodyParameter("id_login", id_login)
            .addBodyParameter("uname", username)
            .addBodyParameter("pass", password)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.dismiss()

                    if (response.getInt("success") == 1){
                        Toast.makeText(this@EditAkunAdm,response.getString("pesan"),Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@EditAkunAdm,response.getString("pesan"),Toast.LENGTH_LONG).show()
                    }

                }

                override fun onError(error: ANError) {
                    loading.dismiss()

                    Toast.makeText(this@EditAkunAdm,error.toString(),Toast.LENGTH_LONG).show()
                }
            })
    }

}