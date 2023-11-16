package com.pragyanSpace.pathcare_management.management.home.model

import com.google.gson.annotations.SerializedName

data class AppointmentResponseData(

    @SerializedName("success"      ) var success      : Boolean?                = null,
    @SerializedName("appointments" ) var appointments : ArrayList<Appointments> = arrayListOf(),
    @SerializedName("message"      ) var message      : String?                 = null,
    @SerializedName("error"        ) var error        : String?                 = null

)

data class Appointments (

    @SerializedName("_id"              ) var Id              : String? = null,
    @SerializedName("user_id"          ) var userId          : String? = null,
    @SerializedName("user_name"        ) var userName        : String? = null,
    @SerializedName("appointment_date" ) var appointmentDate : String? = null

)