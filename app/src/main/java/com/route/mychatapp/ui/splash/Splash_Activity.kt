package com.route.mychatapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.route.mychatapp.R
import com.route.mychatapp.ui.register.registerActivity

class Splash_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper())
            .postDelayed({
                startRegister_Screen()
            }, 2000)
    }

    private fun startRegister_Screen() {
        val intent = Intent(this, registerActivity::class.java)
        startActivity(intent)
        finish()
    }
}
