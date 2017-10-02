package guilherme.tabalipa.areasproject.fragment


import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import guilherme.tabalipa.areasproject.R
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import guilherme.tabalipa.areasproject.utils.PermissionUtils


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private val mPermissionDenied = false

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_map, container, false)

        val mapFragment =  childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return v
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        val ok = PermissionUtils.validate(activity, 1,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ok) {
            // Access to the location has been granted to the app.
            mMap.isMyLocationEnabled = true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
                return
            }
        }
    }
}
