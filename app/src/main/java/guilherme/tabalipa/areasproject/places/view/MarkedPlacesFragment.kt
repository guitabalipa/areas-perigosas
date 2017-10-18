package guilherme.tabalipa.areasproject.places.view


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.map.model.Local
import guilherme.tabalipa.areasproject.map.model.PlacesAdapter
import guilherme.tabalipa.areasproject.repositories.DataStore

/**
 * A simple [Fragment] subclass.
 */
class MarkedPlacesFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_marked_places, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view?.findViewById(R.id.recyclerPlaces)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()

        DataStore.updateLocaisPorUsuario()

        DataStore.liveDataListLocaisPorUsuario.observe(activity, Observer<MutableList<Local>>{
            val adapter = PlacesAdapter(it.orEmpty())
            recyclerView?.adapter = adapter
        })
    }
}
