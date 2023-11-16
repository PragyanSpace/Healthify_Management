package com.pragyanSpace.pathcare_management.appointmentDetail.model

import com.google.gson.annotations.SerializedName

data class CreateAppointmentResModel(
    @SerializedName("user_id"    ) var userId     : String? = null,
    @SerializedName("doctor_id"  ) var doctorId   : String? = null,
    @SerializedName("department" ) var department : String? = null
)