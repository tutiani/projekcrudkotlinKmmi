package com.example.psbtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psbtk.databinding.ActivityDataPendaftarBinding
import com.androidnetworking.error.ANError

import org.json.JSONArray

import com.androidnetworking.interfaces.JSONArrayRequestListener

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject


class DataPendaftar : AppCompatActivity() {
    private lateinit var binding : ActivityDataPendaftarBinding
    var result = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataPendaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTampil.setHasFixedSize(true)
        binding.rvTampil.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        tampil_siswa()
    }

    fun tampil_siswa(){
        AndroidNetworking.get("http://192.168.0.108/apk_daftar/tampil_siswa.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    result.clear()
                    if (response.getInt("success") == 1){
                        val jsonArray = response.optJSONArray("data")

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.optJSONObject(i)
                            result.add(
                                model(
                                    jsonObject.getString("id_siswa"),
                                    jsonObject.getString("nama"),
                                    jsonObject.getString("jenis_kelamin"),
                                    jsonObject.getString("umur"),
                                    jsonObject.getString("ttl"),
                                    jsonObject.getString("agama"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("nama_wali"),
                                    jsonObject.getString("status_ortu"),
                                    jsonObject.getString("no_telp")
                                )
                            )
                        }
                        val tampil_siswa = adapter(this@DataPendaftar, result)
                        binding.rvTampil.adapter = tampil_siswa

                    }else{
                        Toast.makeText(this@DataPendaftar,response.getString("pesan"), Toast.LENGTH_LONG).show()
                    }

                }

                override fun onError(error: ANError) {
                    Toast.makeText(this@DataPendaftar,error.toString(),Toast.LENGTH_LONG).show()
                }
            })

    }
}