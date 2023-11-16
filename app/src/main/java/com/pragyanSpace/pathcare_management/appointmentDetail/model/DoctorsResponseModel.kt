package com.pragyanSpace.pathcare_management.appointmentDetail.model

import com.google.gson.annotations.SerializedName

data class DoctorsResponseModel (
    @SerializedName("success"      ) var success     : Boolean?           = null,
    @SerializedName("doctor_count" ) var doctorCount : Int?               = null,
    @SerializedName("doctors"      ) var doctors     : ArrayList<Doctors> = arrayListOf(),
    @SerializedName("message"      ) var message     : String?            = null,
    @SerializedName("error"        ) var error       : String?            = null
    )

data class Doctors (

    @SerializedName("_id"        ) var Id         : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("specialist" ) var specialist : String? = null

)