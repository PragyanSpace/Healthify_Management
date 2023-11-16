package com.pragyanSpace.pathcare_management.management.qrScanner.model

import com.google.gson.annotations.SerializedName

data class CheckAppointmentAvailableModel(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("appointment") var appointment: Appointment? = Appointment(),
    @SerializedName("message") var message: String? = null,
    @SerializedName("error") var error: String? = null

)

data class UserId (

    @SerializedName("_id"          ) var Id          : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("dob"          ) var dob         : String? = null,
    @SerializedName("blood_group"  ) var bloodGroup  : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null,
    @SerializedName("gender"       ) var gender      : String? = null

)

data class DoctorId (

    @SerializedName("_id"  ) var Id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Appointment (

    @SerializedName("_id"              ) var Id              : String?   = null,
    @SerializedName("user_id"          ) var userId          : UserId?   = UserId(),
    @SerializedName("appointment_date" ) var appointmentDate : String?   = null,
    @SerializedName("description" ) var description : String?   = null,
    @SerializedName("status"           ) var status          : String?   = null,
    @SerializedName("doctor_id"        ) var doctorId        : DoctorId? = DoctorId()

)
