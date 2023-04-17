package com.pragyanSpace.pathcare_management.register.model

import com.google.gson.annotations.SerializedName


data class RegisterRequestModel (
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("gst_in") val gst: String?,
    @SerializedName("address") val address: String?

)