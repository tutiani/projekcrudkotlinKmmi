package com.example.psbtk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.psbtk.databinding.CustomDataBinding
import org.json.JSONObject

class adapter(private val context: Context, private val results: ArrayList<model>) : RecyclerView.Adapter<adapter.MyHolder>() {
    private var Items = ArrayList<model>()
    init {
        this.Items = results
    }
    inner class MyHolder(val binding: CustomDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            CustomDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val result = Items[position]
        with(holder){
            binding.tvNama.text = result.nama

            binding.ibEdit.setOnClickListener(){
                val a = Intent(context, EditSiswa::class.java)
                a.putExtra("id_siswa", result.id_siswa)
                context.startActivity(a)
            }
            binding.ibHapus.setOnClickListener(){
                hapus(result.id_siswa)
            }
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }

    fun hapus(id_siswa:String){
        AndroidNetworking.post("http://192.168.0.108/apk_daftar/hapus_siswa.php")
            .addBodyParameter("id_siswa", id_siswa)
            //.setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getInt("success") == 1){
                        Toast.makeText(context,response.getString("pesan"), Toast.LENGTH_SHORT).show()
                        (context as Activity).finish()
                    }else{
                        Toast.makeText(context,response.getString("pesan"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(error: ANError) {
                    Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

}