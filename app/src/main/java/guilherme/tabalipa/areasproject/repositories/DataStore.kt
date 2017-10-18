package guilherme.tabalipa.areasproject.repositories

import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.google.firebase.database.*
import guilherme.tabalipa.areasproject.AreasApplication
import guilherme.tabalipa.areasproject.map.model.Local
import kotlin.collections.HashMap

/**
 * Created by guilhermetabalipa on 11/10/17.
 */
open class DataStore {

    companion object {
        val liveDataListLocais: MutableLiveData<MutableList<Local>> = MutableLiveData()
        val liveDataListLocaisPorUsuario: MutableLiveData<MutableList<Local>> = MutableLiveData()

        private val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference

        fun saveLocal(local : Local) {

            val key = mDatabase.child("locais").child(AreasApplication.getInstance().uid).push().key
            val locaisValues = local.toMap()

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("/locais/" + AreasApplication.getInstance().uid + "/" + key, locaisValues)

            mDatabase.updateChildren(childUpdates)
        }

        fun updateLocais() {
            var listLocais: MutableList<Local>

            val locaisListener = object : ValueEventListener {

                override fun onCancelled(erro: DatabaseError) {
                    Toast.makeText(AreasApplication.getInstance().applicationContext, "Erro: " + erro.message, Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listLocais = mutableListOf()
                    dataSnapshot.children.forEach {
                        it.children.mapNotNullTo(listLocais) {
                            it.getValue<Local>(Local::class.java)
                        }
                    }
                    liveDataListLocais.value = listLocais
                }
            }

            val locais = mDatabase.child("locais")
            locais.addValueEventListener(locaisListener)
        }

        fun updateLocaisPorUsuario() {
            var listLocais: MutableList<Local>

            val locaisListener = object : ValueEventListener {

                override fun onCancelled(erro: DatabaseError) {
                    Toast.makeText(AreasApplication.getInstance().applicationContext, "Erro: " + erro.message, Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listLocais = mutableListOf()
                    dataSnapshot.children.mapNotNullTo(listLocais) {
                        it.getValue<Local>(Local::class.java)
                    }

                    liveDataListLocaisPorUsuario.value = listLocais
                }
            }

            val locais = mDatabase.child("locais").child(AreasApplication.getInstance().uid)
            locais.addValueEventListener(locaisListener)
        }
    }
}
