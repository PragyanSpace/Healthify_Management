package com.pragyanSpace.pathcare_management.management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.databinding.ActivityManagementBinding

class ManagementActivity : AppCompatActivity() {
    lateinit var binding: ActivityManagementBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_management)
        setupViews()

    }
    private fun setupViews() {
        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        setupSmoothBottomMenu()
    }
    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.management_menu_bottom_navbar)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu,navController)
    }
}