package com.pragyanSpace.pathcare_management.appointmentDetail.model

import com.google.gson.annotations.SerializedName

data class AppointmentResponseData2(
    @SerializedName("success"     ) var success     : Boolean?     = null,
    @SerializedName("appointment" ) var appointment : Appointment? = Appointment(),
    @SerializedName("message"     ) var message     : String?      = null,
    @SerializedName("error"       ) var error       : String?      = null
)

data class Appointment (
    @SerializedName("_id"              ) var Id              : String? = null,
    @SerializedName("hospital_id"      ) var hospitalId      : String? = null,
    @SerializedName("user_id"          ) var userId          : String? = null,
    @SerializedName("appointment_date" ) var appointmentDate : String? = null,
    @SerializedName("urgency"          ) var urgency         : Int?    = null,
    @SerializedName("req_date"         ) var reqDate         : String? = null,
    @SerializedName("__v"              ) var _v              : Int?    = null,
    @SerializedName("status"           ) var status          : String? = null,
    @SerializedName("doctor_id"        ) var doctorId        : String? = null
        )

data class Appointments(

    @SerializedName("_id") var Id: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("user") var user: User? = User(),
    @SerializedName("appointment_date") var appointmentDate: String? = null
)

data class User(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("blood_group") var bloodGroup: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("address") var address: Address? = Address()
)


data class Address(
    @SerializedName("state") var state: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("district") var district: String? = null,
    @SerializedName("pin_code") var pinCode: Int? = null
)
