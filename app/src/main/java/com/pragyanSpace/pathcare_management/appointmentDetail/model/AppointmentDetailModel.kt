package com.pragyanSpace.pathcare_management.appointmentDetail.model

import com.google.gson.annotations.SerializedName

data class AppointmentDetailModel(
    @SerializedName("success"     ) var success     : Boolean?     = null,
    @SerializedName("appointment" ) var appointment : AppointmentData? = AppointmentData(),
    @SerializedName("message"     ) var message     : String?      = null,
    @SerializedName("error"       ) var error       : String?      = null
)

data class UserId (

    @SerializedName("_id"          ) var Id          : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("dob"          ) var dob         : String? = null,
    @SerializedName("blood_group"  ) var bloodGroup  : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null,
    @SerializedName("gender"       ) var gender      : String? = null

)

data class AppointmentData (

    @SerializedName("_id"              ) var Id              : String? = null,
    @SerializedName("user_id"          ) var userId          : UserId? = UserId(),
    @SerializedName("description"      ) var description     : String? = null,
    @SerializedName("appointment_date" ) var appointmentDate : String? = null,
    @SerializedName("urgency"          ) var urgency         : Int?    = null,
    @SerializedName("req_date"         ) var reqDate         : String? = null,
    @SerializedName("doctor_id"        ) var doctorId        : String? = null,
    @SerializedName("status"           ) var status          : String? = null,
    @SerializedName("department"       ) var department      : String? = null

)
