package guilherme.tabalipa.areasproject.fragment


import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import guilherme.tabalipa.areasproject.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.DialogInterface
import com.google.android.gms.maps.model.Marker


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationClient : FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_map, container, false)

        val mapFragment =  childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return v
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission == 0) {
            mMap.isMyLocationEnabled = true

            mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val myLocation = LatLng(location.latitude, location.longitude)
                    animateCamera(myLocation)
                }
            }
        }

        mMap.setOnMapClickListener { latLng ->
            val marker = mMap.addMarker(MarkerOptions().position(latLng))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            dialog(marker)
        }
    }

    private fun dialog(marker: Marker) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.dialog_description, null))
                .setPositiveButton(R.string.save, DialogInterface.OnClickListener { dialog, id -> })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
                    marker.remove()
                })

        val dialog = builder.create()
        dialog.show()
    }

    private fun animateCamera(myLocation: LatLng) {
        val cameraPosition = CameraPosition.Builder()
                .target(myLocation)
                .zoom(15f)
                .bearing(90f)
                .tilt(40f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}
