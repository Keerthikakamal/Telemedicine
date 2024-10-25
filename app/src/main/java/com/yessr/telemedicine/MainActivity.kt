package com.yessr.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yessr.telemedicine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnDoctor.setOnClickListener {
            val intent = Intent(this, DoctorRegistration::class.java)
            startActivity(intent)

        }


        binding.btnPatient.setOnClickListener {

        }
    }
}