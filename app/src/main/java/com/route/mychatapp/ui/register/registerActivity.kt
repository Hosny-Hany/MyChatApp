package com.route.mychatapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.route.mychatapp.databinding.RegisterActivityBinding
import com.route.mychatapp.ui.hoom.HomeActivity
import com.route.mychatapp.ui.login.loginActivity
import com.route.mychatapp.ui.showMessage

class registerActivity : AppCompatActivity(){
    lateinit var ViewBinding: RegisterActivityBinding
    lateinit var ViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initviews()
        SubscribeToLiveData()
    }

    private fun SubscribeToLiveData() {
        ViewModel.MessageLiveData.observe(this) { Message ->
            showMessage(
                message = Message.message ?: "some thing want Error",
                posActionName = "OK",
                posAction = Message.OnPosAction,
                negAction = Message.OnNegAction,
                IsCancelable = Message.IsCancelable,
                NegActionName = Message.OnPosAction
            )
        }
        ViewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(registerEvent: RegisterEvent?) {
        when(registerEvent)
        {
            RegisterEvent.NavigateToHome->{
                NavigateToHome()
            }

            RegisterEvent.NavigateToLogin->{
                NavigateToLogin()
            }

            else -> {

            }
        }
    }
    private fun NavigateToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun NavigateToLogin(){
    val intent = Intent(this, loginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun initviews() {
        ViewBinding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(ViewBinding.root)

        ViewModel = ViewModelProvider(this)[(RegisterViewModel::class.java)]

        ViewBinding.lifecycleOwner = this
        ViewBinding.vm = ViewModel
    }

}