package com.example.psbtk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.psbtk.databinding.ActivityDashboardAdminBinding

class DashboardAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnData.setOnClickListener {
            startActivity(Intent(this@DashboardAdmin, DataPendaftar::class.java))
        }
        binding.btnTbhData.setOnClickListener {
            startActivity(Intent(this@DashboardAdmin, FormPendaftar::class.java))
        }
        binding.btnEditAdm.setOnClickListener {
            startActivity(Intent(this@DashboardAdmin, EditAkunAdm::class.java))
        }
        binding.btnTbhAdm.setOnClickListener {
            startActivity(Intent(this@DashboardAdmin, TambahAkunAdm::class.java))
        }
    }
}