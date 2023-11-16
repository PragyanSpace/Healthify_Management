package com.pragyanSpace.pathcare_management.management.home.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.databinding.FragmentManagementHomeBinding
import com.pragyanSpace.pathcare_management.management.home.viewmodel.HomeViewModel
import com.pragyanSpace.pathcare_management.utility.PrefUtil

class ManagementHomeFragment : Fragment() {

    lateinit var binding: FragmentManagementHomeBinding
    var token: String? = null
    lateinit var viewmodel: HomeViewModel
    var frontShown:Boolean= true
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
            if(it.appointments.isNotEmpty()) {
                binding.appopintmentRv.layoutManager =
                    LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false);
                var appointmentAdapter = HomeAdapter(
                    requireContext(),
                    ArrayList(it.appointments)
                )
                binding.appopintmentRv.adapter = appointmentAdapter
            }
            else
            {
                binding.appopintmentRv.visibility=View.GONE
                binding.noAppointmentTv.visibility=View.VISIBLE
            }
        })
    }

    private fun intiListener() {

        binding.username=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.USERNAME,"").toString()+","
        Log.d("username",PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.USERNAME,"").toString())
//        binding.homeCard.setOnClickListener {
//            val rotateAnimation: Animation =
//                AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
//            val fadeInAnimation: Animation =
//                AnimationUtils.loadAnimation(context, R.anim.fade_in)
//
//            it.startAnimation(rotateAnimation)
//            if(frontShown) {
//                binding.backView.visibility = View.VISIBLE
//                binding.frontView.visibility = View.GONE
//                binding.backView.startAnimation(fadeInAnimation)
//                frontShown=false
//            }
//            else
//            {
//                binding.backView.visibility = View.GONE
//                binding.frontView.visibility = View.VISIBLE
//                binding.frontView.startAnimation(fadeInAnimation)
//                frontShown=true
//            }
//        }

        binding.refreshLayout.setOnRefreshListener {
            callAppointmentsApi()
            binding.refreshLayout.isRefreshing = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun callAppointmentsApi() {
        val id=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.ID, "")
        viewmodel.hospitalApiCall(token.toString(), id!!)
    }
}