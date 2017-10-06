package guilherme.tabalipa.areasproject.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.fragment.MapFragment
import guilherme.tabalipa.areasproject.fragment.MarkedPlacesFragment

/**
 * Created by guilhermetabalipa on 06/10/17.
 */
class TabsAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MapFragment()
            else -> return MarkedPlacesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return context.getString(R.string.tab_map)
            else -> return context.getString(R.string.tab_places)
        }
    }

    override fun getCount(): Int = 2

}