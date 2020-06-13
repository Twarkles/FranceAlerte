package com.example.francealerte

import Alerte
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient


    private lateinit var mMap: GoogleMap

    val PERMISSION_ID = 42
    val altitude = 0
    val longitude = 0

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ref = FirebaseDatabase.getInstance().getReference("Alertes")
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        var lv: ListView = findViewById(R.id.listv )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Alertes")

//myRef.addChildEventListener(object : ChildEventListener {
//    override fun onCancelled(p0: DatabaseError) {
//
//    }
//
//    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//        val value : String = p0.getValue(String.class)
//    }
//
//    override fun onChildRemoved(p0: DataSnapshot) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//} )
//        myRef.addValueEventListener(object: ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                showData(DataSnapshot)
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        });

//     FirebaseDatabase.getInstance().reference
//         .child("Alertes")
//         .child ("1")
//         .addListenerForSingleValueEvent(object : ValueEventListener {
//             override fun onCancelled(p0: DatabaseError) {
//                 TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//             }
//
//             override fun onDataChange(p0: DataSnapshot) {
//                 TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//             }
//
//         } )




    }

//    private fun showData(dataSnapshot: DataSnapshot) {
//        for (ds : DataSnapshot in dataSnapshot.children) {
//
//        }
//    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    public fun getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        //findViewById<TextView>(R.id.latitude).text = "Rafraichir"
                        requestNewLocationData()
                    } else {
                        findViewById<TextView>(R.id.latitude).text = location.latitude.toString()
                        findViewById<TextView>(R.id.longitude).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, LocationCallback(),
            Looper.myLooper()
        )
    }



}
