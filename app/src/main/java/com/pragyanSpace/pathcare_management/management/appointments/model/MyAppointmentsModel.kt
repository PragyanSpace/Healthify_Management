package com.pragyanSpace.pathcare_management.management.appointments.model

import com.google.gson.annotations.SerializedName

data class MyAppointmentsModel(

    @SerializedName("success"           ) var success          : Boolean?                = null,
    @SerializedName("appointment_count" ) var appointmentCount : Int?                    = null,
    @SerializedName("appointments"      ) var appointments     : ArrayList<Appointments> = arrayListOf(),
    @SerializedName("message"           ) var message          : String?                 = null,
    @SerializedName("error"             ) var error            : String?                 = null

)

data class DoctorId (

    @SerializedName("_id"  ) var Id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Appointments (

    @SerializedName("_id"              ) var Id              : String?   = null,
    @SerializedName("user_id"          ) var userId          : UserId?   = UserId(),
    @SerializedName("appointment_date" ) var appointmentDate : String?   = null,
    @SerializedName("doctor_id"        ) var doctorId        : DoctorId? = DoctorId()

)

data class UserId (

    @SerializedName("_id"  ) var Id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)