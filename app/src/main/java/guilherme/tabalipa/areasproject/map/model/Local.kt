package guilherme.tabalipa.areasproject.map.model

/**
 * Created by guilhermetabalipa on 10/10/17.
 */
data class Local(val descricao:String = "", val latitude:Double = 0.0, val longitude:Double = 0.0) {

    fun toMap() : Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result.put("descricao", descricao)
        result.put("latitude", latitude)
        result.put("longitude", longitude)

        return result
    }
}