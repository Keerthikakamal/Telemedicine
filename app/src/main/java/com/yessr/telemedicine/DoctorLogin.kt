package com.yessr.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yessr.telemedicine.databinding.ActivityDoctorLoginBinding
import com.yessr.telemedicine.databinding.ActivityDoctorRegistrationBinding

class DoctorLogin : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_login)

        binding = ActivityDoctorLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, DoctorRegistration::class.java)
            startActivity(intent)
        }

        binding.btnDoctorSubmit.setOnClickListener {
            val number = binding.etDoctorPhoneNumber.text.trim().toString()
            val password = binding.etDoctorPassword.text.trim().toString()

            val intent = Intent(this, DoctorHome::class.java)

            intent.putExtra("PHONE_NUMBER", number)
            intent.putExtra("PASSWORD", password)

            startActivity(intent)
        }
    }
}