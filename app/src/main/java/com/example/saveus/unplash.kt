package com.example.saveus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper


class unplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unplash)

       val intentLoginActivity=Intent(this,LoginActivity::class.java)
        val intentToBoarding=Intent(this,OnBoardingActivity::class.java)
        val sherd=getSharedPreferences(SHERD_PREFERENCES, MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            if (!sherd.getBoolean(visit,false)) {
                startActivity(intentToBoarding)
            }else{
                startActivity(intentLoginActivity)
            }

        },1000)
    }
}
