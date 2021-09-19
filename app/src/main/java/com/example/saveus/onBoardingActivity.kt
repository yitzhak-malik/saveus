package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val intent= Intent(this,LoginActivity::class.java)


        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = PageAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
          TabLayoutMediator(tabLayout,viewPager){ tab, position ->

          }.attach()

       val sherd =getSharedPreferences(SHERD_PREFERENCES, MODE_PRIVATE)
       val handler=Handler(Looper.getMainLooper())
       val runnable=Runnable{
           sherd.edit().putBoolean(visit,true).apply()
           startActivity(intent)
       }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
              if(position==2){
             handler.postDelayed(runnable,5000)
              }
                else{
                    handler.removeCallbacks(runnable)
                }
            }

        })

     }




}
