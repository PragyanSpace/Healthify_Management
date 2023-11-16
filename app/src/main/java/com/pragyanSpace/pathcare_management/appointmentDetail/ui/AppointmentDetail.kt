package com.pragyanSpace.pathcare_management.appointmentDetail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pragyanSpace.pathcare_management.utility.PrefUtil
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel.AppointmentDetailViewmodel
import com.pragyanSpace.pathcare_management.databinding.ActivityAppointmentDetailBinding
import com.pragyanSpace.pathcare_management.management.home.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointmentDetail : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentDetailBinding
    lateinit var token:String
    lateinit var viewmodel:AppointmentDetailViewmodel
    var id:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_appointment_detail)
        viewmodel = ViewModelProvider(this).get(AppointmentDetailViewmodel::class.java)

        id=intent.getStringExtra("id")
        var viewmodel = ViewModelProvider(this).get(AppointmentDetailViewmodel::class.java)
        token = PrefUtil(this).sharedPreferences?.getString(PrefUtil.TOKEN, "").toString()
        observeApi()
        observeAppointmentDetailApi()

        callAppointmentDetailApi()

        binding.accept.setOnClickListener {
            val bottomSheetDialog = AssignDoctorBottomSheet(token,id,null)
            bottomSheetDialog.isCancelable=true
            bottomSheetDialog.show(supportFragmentManager, "CustomBottomSheetDialogFragment")

        }

        binding.reject.setOnClickListener {
            viewmodel.resApiCall(token,id.toString(),"R",null,null)
        }

    }

    private fun observeAppointmentDetailApi() {
        viewmodel.appointmentDetailResponse.observe(this, Observer {

            binding.name=it.appointment?.userId?.name
            binding.dob=it.appointment?.userId?.dob
            binding.blood=it.appointment?.userId?.bloodGroup
            binding.email=it.appointment?.userId?.email
            binding.contact=it.appointment?.userId?.phoneNumber
            binding.date=it.appointment?.appointmentDate
            binding.description=it.appointment?.description

        })
    }

    private fun observeApi() {
        viewmodel?.appointmentResponse2?.observe(this, Observer {
            if (it.success == true) {
                binding.accept.visibility=View.GONE
                binding.reject.visibility=View.GONE
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.Main).launch {

                    delay(2000)
                    finish()

                }
            }
        })
    }

    private fun callAppointmentDetailApi() {
        val token =
            PrefUtil(this).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewmodel?.getAppointmentDetail(token.toString(),id.toString())

    }
    }