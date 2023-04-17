package com.pragyanSpace.pathcare_management.management.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pragyanSpace.pathcare_management.management.home.model.AppointmentResponseData
import com.pragyanSpace.pathcare_management.management.home.repository.HomeRepository

class HomeViewModel : ViewModel() {
    private val repository= HomeRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val appointmentResponse :LiveData<AppointmentResponseData>

    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.appointmentResponse = repository.appointmentResponseModel
    }

    fun hospitalApiCall(token: String, hosId:String){
        repository.getAppointments(token,hosId)
    }
}