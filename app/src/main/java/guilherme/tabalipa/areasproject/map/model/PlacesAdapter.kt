package guilherme.tabalipa.areasproject.map.model

import android.content.Context
import android.location.Geocoder
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import guilherme.tabalipa.areasproject.R
import java.util.*

/**
 * Created by guilhermetabalipa on 17/10/17.
 */
class PlacesAdapter(val locais: List<Local>) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    lateinit var context : Context
    lateinit var local : Local

    class PlacesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDescricao: TextView = view.findViewById(R.id.tv_descricao)
        var tvEndereco: TextView = view.findViewById(R.id.tv_endereco)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        context = holder.itemView.context
        local = locais[position]

        holder.tvDescricao.text = local.descricao
        holder.tvEndereco.text = getAddress()
    }

    override fun getItemCount() = this.locais.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlacesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.place_card, parent, false)
        return PlacesViewHolder(view)
    }

    private fun getAddress() : String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(local.latitude, local.longitude, 1)
        return addresses[0].getAddressLine(0)
    }
}