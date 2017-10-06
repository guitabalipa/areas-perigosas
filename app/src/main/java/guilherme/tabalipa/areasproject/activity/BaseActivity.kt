package guilherme.tabalipa.areasproject.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import guilherme.tabalipa.areasproject.R

/**
 * Created by guilhermetabalipa on 06/10/17.
 */
open class BaseActivity : AppCompatActivity() {

    protected val context: Context get() = this

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_exit) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}