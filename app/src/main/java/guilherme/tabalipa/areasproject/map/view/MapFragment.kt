package guilherme.tabalipa.areasproject.map.view


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
import android.arch.lifecycle.Observer
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import android.widget.EditText
import com.google.android.gms.maps.model.*
import guilherme.tabalipa.areasproject.map.model.Local
import guilherme.tabalipa.areasproject.repositories.DataStore


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

            mFusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val myLocation = LatLng(it.latitude, it.longitude)
                    animateCamera(myLocation)
                }
            }
        }

        DataStore.updateLocais()

        DataStore.liveDataListLocais.observe(activity, Observer<MutableList<Local>>{
            it?.forEach {
                val marker = mMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                marker.title = it.descricao
            }
        })

        mMap.setOnMapClickListener {
            dialog(it)
        }

        mMap.setOnInfoWindowClickListener {
            it.hideInfoWindow()
        }

    }

    private fun dialog(latLng : LatLng) {
        val builder = AlertDialog.Builder(activity)

        val view = View.inflate(activity, R.layout.dialog_description, null)
        builder.setView(view)
                .setPositiveButton(R.string.save, { dialog, id ->
                    val edDesc = view.findViewById<EditText>(R.id.etDescription)
                    val descricao = edDesc.text.toString()

                    val local = Local(descricao, latLng.latitude, latLng.longitude)

                    DataStore.saveLocal(local)
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
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
