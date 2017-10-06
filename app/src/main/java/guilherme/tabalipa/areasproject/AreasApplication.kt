package guilherme.tabalipa.areasproject

import android.app.Application

/**
 * Created by guilhermetabalipa on 06/10/17.
 */
class AreasApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appInstance = this
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