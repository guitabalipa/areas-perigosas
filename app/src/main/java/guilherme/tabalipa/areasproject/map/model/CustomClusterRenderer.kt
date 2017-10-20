package guilherme.tabalipa.areasproject.map.model

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import guilherme.tabalipa.areasproject.R

/**
 * Created by guilhermetabalipa on 20/10/17.
 */
class CustomClusterRenderer(context: Context?, map: GoogleMap?, clusterManager: ClusterManager<Item>?) : DefaultClusterRenderer<Item>(context, map, clusterManager) {

    private val mContext : Context? = context

    override fun onBeforeClusterItemRendered(item: Item?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
        markerOptions?.title(item?.mTitle)
    }

    override fun getColor(clusterSize: Int): Int {
        return Color.parseColor("#C40507")
    }
}