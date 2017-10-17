package guilherme.tabalipa.areasproject

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by guilhermetabalipa on 06/10/17.
 */
class AreasApplication : Application() {

    var mFirebaseAuth: FirebaseAuth? = null
    var uid: String? = null

    override fun onCreate() {
        super.onCreate()

        appInstance = this
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    companion object {
        private var appInstance : AreasApplication? = null

        fun getInstance() : AreasApplication {
            if (appInstance == null) {
                throw IllegalStateException("Faltou a classe [project]Application estendendo BaseApplication e android:name no Application do AndroidManifest.xml!")
            }
            return appInstance!!
        }
    }
}