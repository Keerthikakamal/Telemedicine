package com.yessr.telemedicine.model

data class Appointment(
    var patientMobileNumber: String = "",
    var doctorId: String = "",
    var doctorName: String = "",
    var appointmentTime: String = ""
)
