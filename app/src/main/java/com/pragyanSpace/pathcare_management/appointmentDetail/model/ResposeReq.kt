package com.pragyanSpace.pathcare_management.appointmentDetail.model

import com.google.gson.annotations.SerializedName

data class ResposeReq(
    @SerializedName("status") var status: String? = null,
    @SerializedName("doctor_id") var docId: String? = null,
    @SerializedName("department") var department: String? = null
)
