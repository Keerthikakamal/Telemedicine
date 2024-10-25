package com.yessr.telemedicine

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityDoctorRegistrationBinding
import java.util.*

class DoctorRegistration : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorRegistrationBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Time picker for 'From' time
        binding.etFromTime.setOnClickListener {
            showTimePickerDialog(binding.etFromTime)
        }

        // Time picker for 'To' time
        binding.etToTime.setOnClickListener {
            showTimePickerDialog(binding.etToTime)
        }

        // Handle form submission
        binding.btnDoctorSubmit.setOnClickListener {

            val name = binding.etDoctorName.text.trim().toString()
            val profession = binding.etDoctorProfession.text.trim().toString()
            val location = binding.etDoctorLocation.text.trim().toString()
            val phoneNumber = binding.etDoctorPhoneNumber.text.trim().toString()
            val password = binding.etDoctorPassword.text.trim().toString()
            val fromTime = binding.etFromTime.text.trim().toString()
            val toTime = binding.etToTime.text.trim().toString()

            // Validation
            if (name.isEmpty() || profession.isEmpty() || location.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fromTime.isEmpty() || toTime.isEmpty()) {
                Toast.makeText(this, "Please select availability time.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerDoctor(name, profession, location, phoneNumber, fromTime, toTime)
        }
    }
    
    private fun registerDoctor(name: String, profession: String, location: String, phoneNumber: String, fromTime: String, toTime: String) {
        // Create a map for doctor details
        val doctorMap = hashMapOf(
            "name" to name,
            "profession" to profession,
            "location" to location,
            "phoneNumber" to phoneNumber,
            "fromTime" to fromTime,
            "toTime" to toTime,
            "type" to "doctor"
        )

        // Save data in the "doctors" collection, auto-generating the document ID
        db.collection("doctors")
            .add(doctorMap)
            .addOnSuccessListener {
                // Show success message and navigate to DoctorHome activity
                Toast.makeText(this, "Doctor registered successfully", Toast.LENGTH_SHORT).show()

                // Pass data to the next activity
                val intent = Intent(this, DoctorHome::class.java)
                intent.putExtra("NAME", name)
                intent.putExtra("PROFESSION", profession)
                intent.putExtra("LOCATION", location)
                intent.putExtra("PHONE_NUMBER", phoneNumber)
                intent.putExtra("FROM_TIME", fromTime)
                intent.putExtra("TO_TIME", toTime)

                startActivity(intent)
            }
            .addOnFailureListener { e ->
                // Handle the error
                Toast.makeText(this, "Failed to register: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to show time picker dialog
    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            editText.setText(time)
        }, hour, minute, true)

        timePickerDialog.show()
    }
}