package guilherme.tabalipa.areasproject.map.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by guilhermetabalipa on 20/10/17.
 */
class Item(lat: Double, lng: Double) : ClusterItem {

    var mTitle : String = ""
    private var mPosition: LatLng = LatLng(lat, lng)

    override fun getPosition(): LatLng {
        return mPosition
    }

}