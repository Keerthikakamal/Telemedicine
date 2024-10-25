package com.yessr.telemedicine

import Patient
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yessr.telemedicine.databinding.ListItemPatientBinding

class PatientAdapter(private val patients: List<Patient>) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    inner class PatientViewHolder(val binding: ListItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ListItemPatientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.binding.apply {
            tvPatientName.text = patient.name
            tvPatientPhone.text = patient.phoneNumber
            tvPatientLocation.text = patient.location
        }
    }

    override fun getItemCount() = patients.size
}