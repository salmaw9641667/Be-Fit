package uk.ac.tees.mad.W9641667

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import androidx.core.app.ActivityCompat
import android.Manifest
import com.google.android.libraries.places.api.model.Place.Field
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient


class Gym : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fLoc: FusedLocationProviderClient
    private val locationRequestCode = 1000
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gym)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyAMqXNuXNERzqzRHWkipViM-lasA0AIQns")
        }

        placesClient = Places.createClient(this)


        fLoc = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), locationRequestCode)
        } else {
            mMap.isMyLocationEnabled = true
            fLoc.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    searchNearbyGyms(currentLatLng)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(reqCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(reqCode, permissions, grantResults)
        if (reqCode == locationRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.isMyLocationEnabled = true
                }
            }
        }
    }

    private fun searchNearbyGyms(location: LatLng) {
        val placeFields = listOf(Field.NAME, Field.LAT_LNG)
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val response = placesClient.findCurrentPlace(request)
        response.addOnCompleteListener { task: Task<FindCurrentPlaceResponse> ->
            if (task.isSuccessful) {
                val response = task.result
                response?.placeLikelihoods?.forEach { placeLikelihood ->
                    val place = placeLikelihood.place
                    if (place.types?.contains(Place.Type.GYM) == true) {
                        place.latLng?.let {
                            mMap.addMarker(MarkerOptions().position(it).title(place.name))
                        }
                    }
                }
            } else {

                task.exception?.let {

                }
            }
        }

    }

}