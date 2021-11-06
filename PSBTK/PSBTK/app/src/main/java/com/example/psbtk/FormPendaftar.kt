package com.example.psbtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.psbtk.databinding.ActivityFormPendaftarBinding
import org.json.JSONObject

class FormPendaftar : AppCompatActivity() {
    private lateinit var binding : ActivityFormPendaftarBinding
    var jk ="L"
    var status = "Ayah"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPendaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSimpanform.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val umur = binding.etUmur.text.toString()
            val ttl = binding.etTtl.text.toString()
            val agama = binding.etAgama.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val nama_wali = binding.etNamawali.text.toString()
            val no_telp = binding.etNomor.text.toString()

            if (nama.isEmpty()){
                binding.etNama.error="Kosong"
                binding.etNama.requestFocus()
            }else if (umur.isEmpty()) {
                binding.etUmur.error = "Kosong"
                binding.etUmur.requestFocus()
            }else if (ttl.isEmpty()) {
                binding.etTtl.error = "Kosong"
                binding.etTtl.requestFocus()
            }else if (agama.isEmpty()) {
                binding.etAgama.error = "Kosong"
                binding.etAgama.requestFocus()
            }else if (alamat.isEmpty()){
                binding.etAlamat.error="Kosong"
                binding.etAlamat.requestFocus()
            }else if (nama_wali.isEmpty()) {
                binding.etNamawali.error = "Kosong"
                binding.etNamawali.requestFocus()
            }else if (no_telp.isEmpty()){
                binding.etNomor.error="Kosong"
                binding.etNomor.requestFocus()
            }else{
                //simpan data
                AndroidNetworking.post("http://192.168.0.108/apk_daftar/form_daftar.php")
                    .addBodyParameter("nama", nama)
                    .addBodyParameter("jenis_kelamin", jk)
                    .addBodyParameter("umur", umur)
                    .addBodyParameter("ttl", ttl)
                    .addBodyParameter("agama", agama)
                    .addBodyParameter("alamat", alamat)
                    .addBodyParameter("nama_wali", nama_wali)
                    .addBodyParameter("status_ortu", status)
                    .addBodyParameter("no_telp", no_telp)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(this@FormPendaftar,response.getString("pesan"),
                                    Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@FormPendaftar,response.getString("pesan"),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }


                        override fun onError(error: ANError) {
                            Toast.makeText(this@FormPendaftar,error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })

            }
        }
        binding.rgJeniskelamin.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.rbL.id) {
                jk = "L"
            } else {
                jk = "P"
            }
        }
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.rbAyah.id) {
                status = "Ayah"
            } else if (checkedId == binding.rbIbu.id) {
                status = "Ibu"
            } else if (checkedId == binding.rbWali.id){
                status = "Wali"
            }
        }
    }
}