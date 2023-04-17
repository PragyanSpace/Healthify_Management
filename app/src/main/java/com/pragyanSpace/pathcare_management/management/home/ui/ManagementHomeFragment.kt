package com.pragyanSpace.pathcare_management.management.home.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pragyanSpace.pathcare_management.management.home.viewmodel.HomeViewModel
import com.pragyanSpace.pathcare_management.utility.PrefUtil
import com.pragyanSpace.pathcare_management.databinding.FragmentManagementHomeBinding
import java.util.ArrayList

class ManagementHomeFragment : Fragment() {

    lateinit var binding: FragmentManagementHomeBinding
    var token: String? = null
    lateinit var viewmodel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagementHomeBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        token = PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        observeApi()
        callAppointmentsApi()
        intiListener()

        return binding.root
    }

    private fun observeApi() {
        viewmodel.appointmentResponse.observe(viewLifecycleOwner, Observer {
            binding.hospitalRv.layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
            var appointmentAdapter = HomeAdapter(
                requireContext(),
                ArrayList(it.appointments)
            )
            binding.hospitalRv.adapter = appointmentAdapter
        })
    }

    private fun intiListener() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun callAppointmentsApi() {
        val id=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.ID, "")
        viewmodel.hospitalApiCall(token.toString(), id!!)
    }
}