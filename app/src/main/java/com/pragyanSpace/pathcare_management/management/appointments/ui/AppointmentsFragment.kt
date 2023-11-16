package com.pragyanSpace.pathcare_management.management.appointments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackvita.pathcare.patient.appointments.viewmodel.MyAppointmentsViewmodel
import com.pragyanSpace.pathcare_management.databinding.FragmentAppointmentsBinding
import com.pragyanSpace.pathcare_management.management.appointments.model.MyAppointmentsModel
import com.pragyanSpace.pathcare_management.utility.PrefUtil

class AppointmentsFragment : Fragment() {

    lateinit var binding: FragmentAppointmentsBinding
    lateinit var viewmodel: MyAppointmentsViewmodel
    lateinit var myAppointments: MyAppointmentsModel
    var token:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentsBinding.inflate(layoutInflater, container, false)
        viewmodel=ViewModelProvider(this).get(MyAppointmentsViewmodel::class.java)
        observeMyAppointmentApiResponse()
        observeProgress()

        token = PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewmodel.myAppointmentsApiCall(token.toString())

        binding.refreshLayout.setOnRefreshListener {
            viewmodel.myAppointmentsApiCall(token.toString())
            binding.refreshLayout.isRefreshing = false
        }





        return binding.root
    }

    private fun observeMyAppointmentApiResponse()
    {
        viewmodel.hospitalResponse.observe(viewLifecycleOwner, Observer {
            if(it.appointments.isEmpty()) {
                binding.noAppointmentTv.visibility = View.VISIBLE
                binding.appointmentRV.visibility=View.GONE
            }
            else {
                binding.noAppointmentTv.visibility = View.GONE
                binding.appointmentRV.visibility=View.VISIBLE
                myAppointments = it
                binding.appointmentRV.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.appointmentRV.adapter =
                    MyAppointmentsAdapter(requireContext(), it.appointments)
            }
        })
    }

    private fun observeProgress()
    {
        viewmodel.showProgress.observe(viewLifecycleOwner, Observer {
            if(it)
            {
                binding.appointmentRV.visibility=View.GONE
                binding.progressBar.visibility=View.VISIBLE
            }
            else
            {
                binding.appointmentRV.visibility=View.VISIBLE
                binding.progressBar.visibility=View.GONE
            }
        })
    }
}