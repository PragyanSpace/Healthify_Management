package com.pragyanSpace.pathcare_management.management.profile.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pragyanSpace.pathcare_management.management.profile.viewmodel.ProfileViewmodel
import com.pragyanSpace.pathcare_management.utility.PrefUtil
import com.pragyanSpace.pathcare_management.databinding.FragmentProfileManagementBinding
import com.pragyanSpace.pathcare_management.login.ui.LoginActivity
import com.pragyanSpace.pathcare_management.management.ManagementActivity

class ManagementProfile : Fragment() {
    lateinit var binding: FragmentProfileManagementBinding
    lateinit var viewmodel: ProfileViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileManagementBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(ProfileViewmodel::class.java)
        initListener()
        observeUserApiCall()
        callUserDetailApi()


        return binding.root
    }

    private fun initListener() {
        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        PrefUtil(requireContext()).removeSavedValue()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun callUserDetailApi() {
        val token= PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        val id= PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.ID, "")
        viewmodel.callUserApi(token,id)
    }

    private fun observeUserApiCall() {
        viewmodel.userResponseMutableLiveData.observe(viewLifecycleOwner, Observer {
            binding.name=it.hospital?.name
            var contact=""
            if(it.hospital?.contactNumber?.isNotEmpty()==true) {
                for (i in it.hospital?.contactNumber!!) {
                    contact+="$i,"
                }
            }
            binding.contact=contact
            binding.email=it.hospital?.email
            var dept=""
//            if(it.hospital?.departments?.isNotEmpty()==true) {
//                for (i in it.hospital?.departments!!.get(0).deptName!!) {
//                    dept+="$i,"
//                }
//            }
//            dept= it.hospital?.departments?.get(0)?.deptName.toString()
            binding.departments=dept
            binding.address="${it.hospital!!.address?.city},${it.hospital!!.address?.district},${it.hospital!!.address?.state},${it.hospital!!.address?.pinCode}"
        })
    }
}