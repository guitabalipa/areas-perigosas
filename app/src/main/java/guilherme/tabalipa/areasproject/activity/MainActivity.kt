package guilherme.tabalipa.areasproject.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.adapters.TabsAdapter
import guilherme.tabalipa.areasproject.extensions.setupToolbar

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val locationRequestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        setupToolbar(R.id.toolbar, getString(R.string.app_name), false)
    }

    private fun setupViewPagerTabs() {
        container.offscreenPageLimit = 1
        container.adapter = TabsAdapter(context, supportFragmentManager)
        tabs.setupWithViewPager(container)

        val cor = ContextCompat.getColor(context, R.color.white)
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
}
