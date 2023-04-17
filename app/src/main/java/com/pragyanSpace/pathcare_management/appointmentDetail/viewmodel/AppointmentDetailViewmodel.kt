package com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import com.pragyanSpace.pathcare_management.appointmentDetail.repository.AppointmentDetailRepository

class AppointmentDetailViewmodel:ViewModel() {

    private val repository= AppointmentDetailRepository()
    val appointmentResponse2 : LiveData<AppointmentResponseData2>

    init{
        this.appointmentResponse2 = repository.appointmentResponseModel2
    }

    fun resApiCall(token: String, hosId:String,status:String){
        repository.postResponse(token,hosId, ResposeReq(status))
    }
}