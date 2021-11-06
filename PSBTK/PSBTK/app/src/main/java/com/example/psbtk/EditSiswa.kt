package com.example.psbtk

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.psbtk.databinding.ActivityEditSiswaBinding
import org.json.JSONObject

class EditSiswa : AppCompatActivity() {
    private lateinit var binding: ActivityEditSiswaBinding
    var jk ="L"
    var status = "Ayah"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cari_data(intent.getStringExtra("id_siswa").toString())

        binding.btSimpanedit.setOnClickListener{
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
                //Edit data
                AndroidNetworking.post("http://192.168.0.108/apk_daftar/edit_siswa.php")
                    .addBodyParameter("id_siswa", intent.getStringExtra("id_siswa"))
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
                                Toast.makeText(this@EditSiswa,response.getString("pesan"),
                                    Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@EditSiswa,response.getString("pesan"),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onError(error: ANError) {
                            Toast.makeText(this@EditSiswa,error.toString(), Toast.LENGTH_SHORT).show()
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

    /*
    fun update(id_siswa: String, nama: String, alamat: String, jk: String) {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post("http://192.168.100.8/materi/crud/edit.php")
            .addBodyParameter("id_crud", id_siswa)
            .addBodyParameter("nama", nama)
            .addBodyParameter("alamat", alamat)
            .addBodyParameter("jenis_kelamin", jk)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.dismiss()

                    if (response.getInt("success") == 1) {
                        Toast.makeText(
                            this@EditSiswa,
                            response.getString("pesan"),
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@EditSiswa,
                            response.getString("pesan"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                override fun onError(error: ANError) {
                    loading.dismiss()

                    Toast.makeText(this@EditSiswa, error.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }
    */

    fun cari_data(id_siswa : String){
        AndroidNetworking.get("http://192.168.0.108/apk_daftar/cari_siswa.php")
            .addQueryParameter("id_siswa", id_siswa)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    if (response.getInt("success") == 1){
                        val jsonobjcet = response.optJSONObject("data")
                        binding.etNama.setText(jsonobjcet.getString("nama"))
                        binding.etTtl.setText(jsonobjcet.getString("ttl"))
                        binding.etUmur.setText(jsonobjcet.getString("umur"))
                        binding.etAgama.setText(jsonobjcet.getString("agama"))
                        binding.etAlamat.setText(jsonobjcet.getString("alamat"))
                        binding.etNamawali.setText(jsonobjcet.getString("nama_wali"))
                        binding.etNomor.setText(jsonobjcet.getString("no_telp"))
                        if (jsonobjcet.getString("jenis_kelamin") == "L"){
                            binding.rbL.isChecked = true
                            jk = "L"
                        }else{
                            binding.rbP.isChecked = true
                            jk = "P"
                        }

                        if (jsonobjcet.getString("status_ortu") == "A"){
                            binding.rbAyah.isChecked = true
                            status = "Ayah"
                        }else if (jsonobjcet.getString("status_ortu") == "I"){
                            binding.rbIbu.isChecked = true
                            status = "Ibu"
                        }else{
                            binding.rbWali.isChecked = true
                            status = "Wali"
                        }
                    }else{
                        Toast.makeText(this@EditSiswa,response.getString("pesan"), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onError(error: ANError) {
                    Toast.makeText(this@EditSiswa,error.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }
}