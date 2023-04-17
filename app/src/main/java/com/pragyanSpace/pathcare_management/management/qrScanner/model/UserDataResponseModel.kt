package com.pragyanSpace.pathcare_management.management.qrScanner.model

import com.google.gson.annotations.SerializedName

data class UserDataResponseModel(
    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("user"    ) var user    : User?    = User()
)

data class User (

    @SerializedName("_id"              ) var Id              : String?                    = null,
    @SerializedName("name"             ) var name            : String?                    = null,
    @SerializedName("email"            ) var email           : String?                    = null,
    @SerializedName("dob"              ) var dob             : String?                    = null,
    @SerializedName("blood_group"      ) var bloodGroup      : String?                    = null,
    @SerializedName("prescriptions"    ) var prescriptions   : ArrayList<String>          = arrayListOf(),
    @SerializedName("appointments"     ) var appointments    : ArrayList<Appointments>    = arrayListOf(),
    @SerializedName("req_appointments" ) var reqAppointments : ArrayList<ReqAppointments> = arrayListOf(),
    @SerializedName("__v"              ) var _v              : Int?                       = null

)
data class Appointments (

    @SerializedName("acpt_appointment" ) var acptAppointment : String? = null,
    @SerializedName("_id"              ) var Id              : String? = null

)
data class ReqAppointments (

    @SerializedName("req_appt" ) var reqAppt : String? = null,
    @SerializedName("_id"      ) var Id      : String? = null

)
