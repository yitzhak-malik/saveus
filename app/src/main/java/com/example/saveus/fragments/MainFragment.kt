package com.example.saveus.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.saveus.R
import com.example.saveus.classes.ListViewModel
import com.example.saveus.classes.RecyclerviewChildItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlin.properties.Delegates
import android.widget.Toast

import android.location.Geocoder
import android.location.Location
import java.io.IOException
import java.util.*


class MainFragment : Fragment(),
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback
{
    private val viewModel: ListViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var startAt by Delegates.notNull<Long>()
    private var sumTime by Delegates.notNull<Long>()
    private lateinit var location:Location
    private lateinit var myLocation: Location

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
        val list = viewModel.list
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

        //start button
        startButton.setOnClickListener {
            startButton.visibility=View.GONE
            stopButton.visibility=View.VISIBLE
            chrono.start()
            startAt = System.currentTimeMillis()
            enableMyLocation()
            location = myLocation
        }
        //stop button
        stopButton.setOnClickListener {
            stopButton.visibility=View.GONE
            startButton.visibility=View.VISIBLE
            chrono.stop()
            sumTime = SystemClock.elapsedRealtime() - chrono.base
            chrono.base = SystemClock.elapsedRealtime()
            list.setNewPlaceInList(RecyclerviewChildItem(location,startAt,sumTime))
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
          if(it!=null){
          myLocation = it
            var  newpp =  CameraPosition( LatLng(it.latitude,it.longitude),15F,map.cameraPosition.tilt,map.cameraPosition.bearing)
         map.animateCamera(CameraUpdateFactory.newCameraPosition(newpp))
          }
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


    fun getAddress(context: Context?, lat: Double, lng: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)
            add = """
             $add
             ${obj.getCountryName()}
             """.trimIndent()
            add = """
             $add
             ${obj.getCountryCode()}
             """.trimIndent()
            add = """
             $add
             ${obj.getAdminArea()}
             """.trimIndent()
            add = """
             $add
             ${obj.getPostalCode()}
             """.trimIndent()
            add = """
             $add
             ${obj.getSubAdminArea()}
             """.trimIndent()
            add = """
             $add
             ${obj.getLocality()}
             """.trimIndent()
            add = """
             $add
             ${obj.getSubThoroughfare()}
             """.trimIndent()
            add
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context,"kdlkjf", Toast.LENGTH_SHORT).show()
            null
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