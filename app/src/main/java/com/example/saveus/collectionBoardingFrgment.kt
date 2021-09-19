package com.example.saveus


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(f:FragmentActivity) :FragmentStateAdapter(f) {
    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> {
                return onBorardingFragment()
            }
            1 -> {
                return onBorardingFragment2()
            }
            2 -> {
                return onBorardingFragment3()
            }
            else -> {
                return onBorardingFragment()
            }
        }

    }





//    override fun getPageTitle(position: Int): CharSequence? {
//
//        when(position) {
//            0 -> {
//                return "5555555"
//            }
//            1 -> {
//                return "Tab 2"
//            }
//            2 -> {
//                return "Tab 3"
//            }
//        }
//        return super.getPageTitle(position)
//    }

}
