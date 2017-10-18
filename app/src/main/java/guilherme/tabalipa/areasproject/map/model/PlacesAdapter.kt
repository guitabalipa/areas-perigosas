package guilherme.tabalipa.areasproject.map.model

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import guilherme.tabalipa.areasproject.R

/**
 * Created by guilhermetabalipa on 17/10/17.
 */
class PlacesAdapter(val locais: List<Local>) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    class PlacesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDescricao: TextView = view.findViewById(R.id.tv_descricao)
        var tvLatitude: TextView = view.findViewById(R.id.tv_latitude)
        var tvLongitude: TextView = view.findViewById(R.id.tv_longitude)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val context = holder.itemView.context
        val local = locais[position]

        holder.tvDescricao.text = local.descricao
        holder.tvLatitude.text = local.latitude.toString()
        holder.tvLongitude.text = local.longitude.toString()
    }

    override fun getItemCount() = this.locais.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlacesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.place_card, parent, false)
        return PlacesViewHolder(view)
    }
}