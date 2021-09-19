package com.example.saveus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intentMainActivity=Intent(this,MainActivity::class.java)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    val skipButton=findViewById<TextView>(R.id.skipButton)
        skipButton.setOnClickListener {
               startActivity(intentMainActivity)

           }

    }
}