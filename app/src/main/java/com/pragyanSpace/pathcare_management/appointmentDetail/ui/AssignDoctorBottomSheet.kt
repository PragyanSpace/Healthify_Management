package com.pragyanSpace.pathcare_management.appointmentDetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel.AppointmentDetailViewmodel
import com.pragyanSpace.pathcare_management.databinding.AssignDoctorBottomSheetBinding
import com.pragyanSpace.pathcare_management.utility.PrefUtil

class AssignDoctorBottomSheet(var token:String?,var id:String?,var from:Int?): BottomSheetDialogFragment(){
        lateinit var binding:AssignDoctorBottomSheetBinding
        var currentYear=0
        lateinit var hosId: String
        lateinit var date: String

        private var viewModel: AppointmentDetailViewmodel? = null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            binding = DataBindingUtil.inflate<AssignDoctorBottomSheetBinding>(inflater, R.layout.assign_doctor_bottom_sheet,container,false)
            viewModel = ViewModelProvider(this).get(AppointmentDetailViewmodel::class.java)
            callDoctorsApi()

            observeAppointmentApiResponse()
            observeShowProgress()



            return binding.root
        }


        private fun callDoctorsApi() {
            val token =
                PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
            viewModel?.getDoctors(token.toString())

        }


        private fun observeAppointmentApiResponse() {
            viewModel?.doctorsResponse?.observe(viewLifecycleOwner, Observer {
                binding.doctorsRV.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.doctorsRV.adapter=DoctorsRecyclerViewAdapter(requireContext(),it.doctors,token,id,viewModel as AppointmentDetailViewmodel,from)
            })

        }

        private fun observeShowProgress() {
            viewModel?.showProgress?.observe(this){

                if(it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.doctorsRV.visibility= View.GONE
                }
                else {

                    binding.progressBar.visibility= View.GONE
                    binding.doctorsRV.visibility= View.VISIBLE
                }

            }

        }


}