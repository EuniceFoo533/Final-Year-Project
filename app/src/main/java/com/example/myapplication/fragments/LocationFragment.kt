package com.example.myapplication.fragments
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class LocationFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var currentLatLng: LatLng

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var locationRef: CollectionReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        val button = view.findViewById<Button>(R.id.btnGetLocation)
        button.setOnClickListener {
            if (::currentLatLng.isInitialized) {
                var latitudeNo = getView()?.findViewById<TextView>(R.id.tvLatitude)
                var longitudeNo = getView()?.findViewById<TextView>(R.id.tvLongitude)
                addMarkerAtCurrentLocation(latitudeNo,longitudeNo)
            }
        }


        val btnSaveLocation = view.findViewById<Button>(R.id.btnSaveLocation)
        btnSaveLocation?.setOnClickListener {
            var latitudeNo = getView()?.findViewById<TextView>(R.id.tvLatitude)
            var longitudeNo = getView()?.findViewById<TextView>(R.id.tvLongitude)

            if(latitudeNo!!.text.isNotEmpty() && longitudeNo!!.text.isNotEmpty())
            {
                val latitude = latitudeNo?.text.toString()
                val longitude = longitudeNo?.text.toString()

                val addLocationFragment = AddLocationFragment.newInstance(latitude, longitude)
                val fragmentManager = requireFragmentManager()
                fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, addLocationFragment)
                    .addToBackStack(null) // Optional: Add the transaction to the back stack
                    .commit()
            }

            else
            {
                Toast.makeText(context,"Please GET CURRENT LOCATION first",Toast.LENGTH_SHORT).show()
            }






        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        locationRef = db.collection("parking")


    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
            return
        }

        // Enable current location
        googleMap.isMyLocationEnabled = true

        // Get current location
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM))
            }
        }
    }

    private fun addMarkerAtCurrentLocation(latitudeNo: TextView?, longitudeNo: TextView?) {
        googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))


        latitudeNo?.text = "${currentLatLng.latitude}"
        longitudeNo?.text = "${currentLatLng.longitude}"


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
    }
}
