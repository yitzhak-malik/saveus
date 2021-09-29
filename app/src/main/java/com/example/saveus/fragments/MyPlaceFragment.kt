package com.example.saveus.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.saveus.R





class MyPlaceFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_my_place, container, false)
        val theMyPlace =view.findViewById<TextView>(R.id.theMyPlace)
        val onTheMap =view.findViewById<TextView>(R.id.onTheMap)
        theMyPlace.setOnClickListener{
            it.setBackgroundResource(R.color.main)
            onTheMap.setBackgroundResource(R.color.bacegrondOfMyPlace)
        }
        onTheMap.setOnClickListener{
            it.setBackgroundResource(R.color.main)
            theMyPlace.setBackgroundResource(R.color.bacegrondOfMyPlace)
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MyPlaceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}