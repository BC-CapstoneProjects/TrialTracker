package com.kinisi.trailtracker.ui.search

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import com.kinisi.trailtracker.R
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.*
import android.R.attr.name
import android.R.attr.name
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import com.google.firebase.firestore.FieldValue
import org.osmdroid.util.Distance


class Speedometer: AppCompatActivity(), OnMapReadyCallback {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    var init_Lat = 0.0
    var init_long = 0.0
    var deltaLngMeters = 0.0
    var deltaLatMeters = 0.0
    var marker = LatLng(0.0,0.0)
    var i = 0
    var x = 0
    var distance = 0.0
    var speed = 0.0f
    lateinit var btnStartupdate: Button
    lateinit var btnStopUpdates: Button
    lateinit var txtLat: TextView
    lateinit var txtLong: TextView
    lateinit var txtTime: TextView
    lateinit var txtDistance: TextView
    lateinit var txtSpeed: TextView
    var previousLocation: Location? = null
    private lateinit var mMap: GoogleMap
    var current = 0
    var locations: ArrayList<LatLng> = ArrayList()
    var Distance: ArrayList<Double> = ArrayList()
    var Speed: ArrayList<Float> = ArrayList()
    var averageSpeed: ArrayList<Float> = ArrayList()
    var count = 0
    private var polyline: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(com.kinisi.trailtracker.R.layout.activity_speedometer)
        mLocationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime= 100
        }
        btnStartupdate = findViewById(R.id.btn_start_upds)
        btnStopUpdates = findViewById(R.id.btn_stop_upds)
        txtLat = findViewById(R.id.txtLat)
        txtLong = findViewById(R.id.txtLong)
        //txtTime = findViewById(R.id.txtTime)
        txtDistance = findViewById(R.id.txtDistance)
        txtSpeed = findViewById(R.id.txtSpeed)




        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }


        btnStartupdate.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
                btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true
                val simpleChronometer = findViewById(R.id.simpleChronometer) as Chronometer
                simpleChronometer.setBase(SystemClock.elapsedRealtime())
                simpleChronometer.start()
                simpleChronometer.setFormat("Timer:   %s")
                simpleChronometer.start()
            }
        }

        btnStopUpdates.setOnClickListener {
            stoplocationUpdates()
            // txtTime.text = "Updates Stopped"
            btnStartupdate.isEnabled = true
            btnStopUpdates.isEnabled = false
            val simpleChronometer = findViewById(R.id.simpleChronometer) as Chronometer
            simpleChronometer.stop()

        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
                true
            }else{
                // Show the permission request
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val mLocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onLocationChanged(location: Location) {
        // New location has now been determined\
        if (x ==0)
        {
            init_Lat = location.latitude
            init_long = location.longitude
            x++
        }
        deltaLatMeters = location.latitude - init_Lat
        deltaLngMeters = location.longitude - init_long
        val date: Date = (Calendar.getInstance().time)
        val sdf = SimpleDateFormat("hh:mm:ss a")
        //txtTime.text = "Updated at : " + sdf.format(date)
        txtLat.text = "LATITUDE : " + location.latitude
        txtLong.text = "LONGITUDE : " + location.longitude
        distance = truncate((sqrt((deltaLngMeters*deltaLngMeters) + (deltaLatMeters*deltaLatMeters))*11000.57))
        txtDistance.text = "Distance " + distance/100 + "km"
        speed = truncate((location.getSpeed()) *360)
        txtSpeed.text = "Speed " + speed/100 +"km/h"
        if (i ==0)
        {
            marker = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(marker))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15F))
            i++
        }
        var latLng = LatLng(location.latitude, location.longitude)
        locations.add(latLng)
        mMap.addPolyline(PolylineOptions().color(Color.RED).addAll(locations))
        setDb()
        Speed.add(speed/100)






        count++



    }
    protected fun startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime= 100
        }

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return
        }
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    private fun stoplocationUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
        Distance.add(distance/100)
        setDbDistance()
        setDbSpeed()

    }

    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()


    }
    private fun setDb() {
        Firebase.firestore
            .collection("userActiveLocation")
            .document("ciJDSJnCFn6yCU9et7qK")

        val userdetail = HashMap<String, Any>()
        userdetail["userActiveLocation"] = locations
        Firebase.firestore.collection("userActiveLocation").document("ciJDSJnCFn6yCU9et7qK")
            .set(userdetail)
            .addOnSuccessListener { success ->

            }
            .addOnFailureListener { exception ->
                Log.e("Data Failed", "To added because ${exception}")
            }


    }

    private fun setDbDistance(){
        Firebase.firestore
            .collection("userTotalDistance")
            .document("ciJDSJnCFn6yCU9et7qK")

        val userDistance = HashMap<String, Any>()
        userDistance["userTotalDistance"] = Distance
        Firebase.firestore.collection("userTotalDistance").document("ciJDSJnCFn6yCU9et7qK")
            .set(userDistance)
            .addOnSuccessListener { success ->

            }
            .addOnFailureListener { exception ->
                Log.e("Data Failed", "To added because ${exception}")
            }


    }

    private fun setDbSpeed(){
        averageSpeed.add(Speed.average().toFloat())
        Firebase.firestore
            .collection("userAverageSpeed")
            .document("ciJDSJnCFn6yCU9et7qK")

        val userSpeed = HashMap<String, Any>()
        userSpeed["userAverageSpeed"] = averageSpeed
        Firebase.firestore.collection("userAverageSpeed").document("ciJDSJnCFn6yCU9et7qK")
            .set(userSpeed)
            .addOnSuccessListener { success ->

            }
            .addOnFailureListener { exception ->
                Log.e("Data Failed", "To added because ${exception}")
            }


    }






    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }

}