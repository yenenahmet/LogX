package com.example.logx.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import com.example.logx.R
import com.example.logx.custom.CustomGoogleMapFragment
import com.example.logx.databinding.ActivityMainBinding
import com.example.logx.view.takeaphoto.TakeAPhotoActivity
import com.example.logx.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yenen.ahmet.basecorelibrary.base.extension.showToast
import com.yenen.ahmet.basecorelibrary.base.ui.BaseActivity
import com.yenen.ahmet.location_service.LocationListener
import com.yenen.ahmet.location_service.LocationService

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    R.layout.activity_main
), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private lateinit var locationService:LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val childFragment =
            supportFragmentManager.findFragmentByTag("fragment_map") as? CustomGoogleMapFragment
                ?: return
        childFragment.getMapAsync(this)
        childFragment.setListener(object : CustomGoogleMapFragment.OnTouchListener {
            override fun onTouch() {
                binding.nestedMain.requestDisallowInterceptTouchEvent(true)
            }
        })

        locationService = LocationService(this, true, 0, 0)
        locationService.setListener(object : LocationListener {
            override fun onLocation(location: Location) {
                addMarker(location.latitude, location.longitude)
            }
        })
        locationService.startLocation()

        requestPermissionsForRuntime(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )
    }

    override fun initViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }


    override fun onBindingCreate(binding: ActivityMainBinding) {
        binding.cvImg.setOnClickListener {
            startActivity(ImageActivity::class.java)
        }
        binding.btnICame.setOnClickListener {
            startActivity(TakeAPhotoActivity::class.java)
        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        this.map = p0
    }

    private fun addMarker(latitude: Double, longitude: Double) {
        if (latitude != 0.0 && longitude != 0.0) {
            val location = LatLng(latitude, longitude)
            val options = MarkerOptions().position(location).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
            map?.addMarker(options)
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 9f))
        }
    }

    override fun onRequestPermissionResultForRuntime(isGranted: Boolean) {
        if(!isGranted){
            showToast("Lütfen İzinleri Açınız!")
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 11){
            locationService.startLocation()
        }
    }


}