package com.route.mychatapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.route.mychatapp.databinding.LoginActivityBinding
import com.route.mychatapp.ui.hoom.HomeActivity
import com.route.mychatapp.ui.register.registerActivity
import com.route.mychatapp.ui.showMessage

class loginActivity : AppCompatActivity() {
    lateinit var ViewBinding: LoginActivityBinding
    lateinit var ViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initview()
        SubscribeToLiveData()
    }

    private fun SubscribeToLiveData() {
        ViewModel.MessageLiveData.observe(this) { Message ->
            showMessage(
                message = Message.message ?: "some thing want Error",
                posActionName = "OK",
                posAction = Message.OnPosAction,
                NegActionName = Message.OnPosAction,
                negAction = Message.OnNegAction,
                IsCancelable = Message.IsCancelable
            )
        }
        ViewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(loginEvents: LoginEvents?) {
        when(loginEvents)
        {
            LoginEvents.NavigateToHome->{
                NavigateToHome()
            }

            LoginEvents.NavigateToRegister ->{
                NavigateToRegister()
            }

            else -> {

            }
        }

    }

private fun NavigateToHome(){
    val intent  = Intent(this , HomeActivity::class.java)
    startActivity(intent)
    finish()
}
private fun NavigateToRegister(){
    val intent  = Intent(this , registerActivity::class.java)
    startActivity(intent)
    finish()
}

    private fun initview() {
        ViewBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(ViewBinding.root)

        ViewModel = ViewModelProvider(this)[(LoginViewModel::class.java)]
        ViewBinding.lifecycleOwner = this
        ViewBinding.vm = ViewModel

    }
}