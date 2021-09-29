package com.example.saveus.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.saveus.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlin.math.log


class MainFragment : Fragment(),
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_main, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        view.findViewById<LinearLayout>(R.id.target).setOnClickListener {
            enableMyLocation()

        }
        // timer
        val chrono = view.findViewById(R.id.chronometer) as Chronometer
        chrono.onChronometerTickListener =
            OnChronometerTickListener { chronometer ->
                val time = SystemClock.elapsedRealtime() - chronometer.base
                when(time){

                }
                if (time > 36000000) {
                    chrono.format = "%s"
                }
                else if (time > 3600000) {
                    chrono.format = "0%s"
                }
                else {
                    chrono.format = "00:%s"
                }
            }
        chrono.format="00:%s"

        //button
        val startButton =view.findViewById<LinearLayout>(R.id.startButton)
        val stopButton =view.findViewById<LinearLayout>(R.id.stopButton)
        startButton.setOnClickListener {
            startButton.visibility=View.GONE
            stopButton.visibility=View.VISIBLE
            chrono.start()
        }
        stopButton.setOnClickListener {
            stopButton.visibility=View.GONE
            startButton.visibility=View.VISIBLE
            chrono.stop()
            chrono.base = SystemClock.elapsedRealtime()-35999999
        }

        return view

    }
    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        enableMyLocation()

    }


    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) }
            == PackageManager.PERMISSION_GRANTED) {

        fusedLocationClient.lastLocation.addOnSuccessListener {

            var  newpp =  CameraPosition( LatLng(it.latitude,it.longitude),15F,map.cameraPosition.tilt,map.cameraPosition.bearing)
         map.animateCamera(CameraUpdateFactory.newCameraPosition(newpp))
        }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermissions( arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE )

        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
        }
    }









    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}