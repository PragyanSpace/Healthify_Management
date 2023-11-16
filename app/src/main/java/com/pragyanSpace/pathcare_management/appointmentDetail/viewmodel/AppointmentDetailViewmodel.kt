package com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentDetailModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentReqModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.DoctorsResponseModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import com.pragyanSpace.pathcare_management.appointmentDetail.repository.AppointmentDetailRepository
import com.pragyanSpace.pathcare_management.appointmentDetail.ui.AppointmentDetail
import com.pragyanSpace.pathcare_management.management.profile.model.Departments

class AppointmentDetailViewmodel:ViewModel() {

    private val repository= AppointmentDetailRepository()
    val appointmentResponse2 : LiveData<AppointmentResponseData2>
    val doctorsResponse:LiveData<DoctorsResponseModel>
    val appointmentDetailResponse:LiveData<AppointmentDetailModel>
    val showProgress:LiveData<Boolean>
    val errorMessage : LiveData<String>

    init{
        this.appointmentResponse2 = repository.appointmentResponseModel2
        this.doctorsResponse=repository.doctorResponse
        this.appointmentDetailResponse=repository.appointmentDetailResponse
        this.showProgress=repository.showProgress
        this.errorMessage=repository.errorMessage
    }

    fun resApiCall(token: String, hosId:String,status:String,docId:String?,department:String?){
        repository.postResponse(token,hosId, ResposeReq(status,docId,department))
    }

    fun createAppointment(token:String,request:CreateAppointmentReqModel){
        repository.createAppointment(token,request)
    }

    fun getDoctors(token: String){
        repository.getDoctors(token)
    }

    fun getAppointmentDetail(token: String,aptId:String){
        repository.getAppointmentDetail(token,aptId)
    }

}