 package com.route.mychatapp.ui.hoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import com.route.mychatapp.R
import com.route.mychatapp.databinding.HomeActivityBinding
import com.route.mychatapp.ui.addrooms.AddRoomActivity

 class HomeActivity : AppCompatActivity() {
    lateinit var ViewBinding : HomeActivityBinding
    private val ViewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewBinding = DataBindingUtil.setContentView(this,R.layout.home_activity)
           initview()
        subscribeLiveData()
    }



    private fun subscribeLiveData() {
        ViewModel.events.observe(this,::handle)
    }
    private fun handle(homeEvents: HomeEvents?) {
     when(homeEvents){
         HomeEvents.NavigateToRoom ->{
             NavigateToRooms()
         }

         else -> {

         }
     }
    }

    private fun NavigateToRooms() {
        val intent = Intent(this ,AddRoomActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initview() {
       ViewBinding.vm = ViewModel
        ViewBinding.lifecycleOwner = this
    }
}