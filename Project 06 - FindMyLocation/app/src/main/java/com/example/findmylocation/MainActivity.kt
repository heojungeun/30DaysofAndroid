package com.example.findmylocation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient : FusedLocationProviderClient

    var quizlist = arrayOf(
        "내가 지금 있는 곳",
        "37.575746, 126.976817",
        "튀김 소보로",
        "동성로에서\n1과 2가 만나는 장소",
        "보수동 책 제일 많은 골목",
        "충장로 ㄱㅈ극장"
    )


    var anslist = arrayOf(
        arrayOf(0F,0F),
        arrayOf(37.575746F, 126.976817F),
        arrayOf(36.327707F, 127.427367F),
        arrayOf(35.865530F, 128.593405F),
        arrayOf(35.103121F, 129.027498F),
        arrayOf(35.149873F, 126.912336F)
    )
    var cnt = 0
    var again = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        gpsbtn.setOnClickListener{
            getLastLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if (checkPermissions()){
            if (isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null){
                        requestNewLocationData()
                    }else{
                        curlocation.setText(location.latitude.toString()+" ,\n"+location.longitude.toString())
                        if(cnt == 0 && again==0){
                            quiztxt.setText((cnt+1).toString()+".\n"+quizlist[cnt]+"\n(다음 단계로 갈려면 버튼 누르기)")
                            again = 1
                        } else if((cnt==0 && again==1) || (anslist[cnt][0].minus(0.005) <= location.latitude && location.latitude <= anslist[cnt][0]+0.005
                                    && anslist[cnt][1]-0.005 <= location.longitude && location.longitude <= anslist[cnt][1]+0.005)){
                            cnt += 1
                            quiztxt.setText((cnt+1).toString()+".\n"+quizlist[cnt])
                        }
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
    private fun requestNewLocationData(){
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            curlocation.setText(mLastLocation.latitude.toString()+" ,\n"+mLastLocation.longitude.toString())
        }
    }

    private fun checkPermissions(): Boolean {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                // granted.
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
