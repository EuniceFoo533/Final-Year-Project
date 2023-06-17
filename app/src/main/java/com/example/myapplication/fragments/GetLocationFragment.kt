package com.example.myapplication.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GetLocationFragment : Fragment() , OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var currentLatLng: LatLng


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_location, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return view
    }



    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                GetLocationFragment.PERMISSION_REQUEST_CODE
            )
            return
        }

        // Enable current location
        googleMap.isMyLocationEnabled = true

        // Get current location
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng,
                        GetLocationFragment.DEFAULT_ZOOM
                    ))

            }
        }
    }

    private fun addMarkerAtCurrentLocation(latitudeNo: String?, longitudeNo: String?) {
        val latLng = LatLng(latitudeNo!!.toDouble(),longitudeNo!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {

        private const val DEFAULT_ZOOM = 15f
        private const val PERMISSION_REQUEST_CODE = 123

        private const val ARG_LATITUDE = "latitude"
        private const val ARG_LONGITUDE = "longitude"

        fun newInstance(latitude: Double?, longitude: Double?): GetLocationFragment {
            val fragment = GetLocationFragment()
            val args = Bundle()
            args.putString(ARG_LATITUDE, latitude.toString())
            args.putString(ARG_LONGITUDE, longitude.toString())
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = arguments?.getString(AddLocationFragment.ARG_LATITUDE)
        val longitude = arguments?.getString(AddLocationFragment.ARG_LONGITUDE)

        var latitudeTextView = getView()?.findViewById<TextView>(R.id.tvLatitude)
        var longitudeTextView = getView()?.findViewById<TextView>(R.id.tvLongitude)

        // Use the latitude and longitude values as needed
        // For example, update TextViews with latitude and longitude:
        latitudeTextView?.text = "$latitude"
        longitudeTextView?.text = "$longitude"

        val button = view.findViewById<Button>(R.id.btnGetLocation)
        button.setOnClickListener {
            if (::currentLatLng.isInitialized) {
                var latitudeNo = getView()?.findViewById<TextView>(R.id.tvLatitude)
                var longitudeNo = getView()?.findViewById<TextView>(R.id.tvLongitude)
                addMarkerAtCurrentLocation(latitudeNo?.text.toString(),longitudeNo?.text.toString())
            }
        }

    }
}