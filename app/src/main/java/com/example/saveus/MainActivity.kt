package com.example.saveus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.fragments.AlertsFragment
import com.example.saveus.fragments.MainFragment
import com.example.saveus.fragments.MyPlaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val  SHERD_PREFERENCES ="hhh"
const val  visit= "visit"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      val navBar=findViewById<BottomNavigationView>(R.id.navbar)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        navBar.itemIconTintList=null
        navBar.selectedItemId=R.id.main
        navBar.setOnItemSelectedListener{i->
         when(i.itemId){
          R.id.main -> transaction(MainFragment.newInstance())
          R.id.my_place -> transaction(MyPlaceFragment.newInstance())
          R.id.alerts -> transaction(AlertsFragment.newInstance())
         }
         true
        }
        transaction(MainFragment.newInstance())


    }

     private fun transaction(fragment:Fragment){
        val transaction =supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }



}

