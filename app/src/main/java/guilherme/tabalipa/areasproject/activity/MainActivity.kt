package guilherme.tabalipa.areasproject.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import guilherme.tabalipa.areasproject.AreasApplication
import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.adapters.TabsAdapter
import guilherme.tabalipa.areasproject.extensions.setupToolbar
import guilherme.tabalipa.areasproject.login.LoginActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.OnConnectionFailedListener {

    private val locationRequestCode = 101
    private var mGoogleApiClient : GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        setupToolbar(R.id.toolbar, getString(R.string.app_name), false)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build()
    }

    private fun setupViewPagerTabs() {
        container.offscreenPageLimit = 1
        container.adapter = TabsAdapter(this, supportFragmentManager)
        tabs.setupWithViewPager(container)

        val cor = ContextCompat.getColor(this, R.color.white)
        tabs.setTabTextColors(cor, cor)
    }

    private fun requestPermission() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    locationRequestCode)
        } else {
            setupViewPagerTabs()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults
                .filter { it == PackageManager.PERMISSION_GRANTED && requestCode == locationRequestCode }
                .forEach {
                    setupViewPagerTabs()
                    return
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_exit) {
            AreasApplication.getInstance().mFirebaseAuth?.signOut()
            Auth.GoogleSignInApi.signOut(mGoogleApiClient)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
    }
}
