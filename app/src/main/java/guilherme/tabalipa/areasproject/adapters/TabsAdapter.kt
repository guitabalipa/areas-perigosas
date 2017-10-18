package guilherme.tabalipa.areasproject.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.map.view.MapFragment
import guilherme.tabalipa.areasproject.places.view.MarkedPlacesFragment

/**
 * Created by guilhermetabalipa on 06/10/17.
 */
class TabsAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MapFragment()
            else -> MarkedPlacesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.tab_map)
            else -> context.getString(R.string.tab_places)
        }
    }

    override fun getCount(): Int = 2
}