package guilherme.tabalipa.areasproject.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import guilherme.tabalipa.areasproject.R
import guilherme.tabalipa.areasproject.adapters.TabsAdapter
import guilherme.tabalipa.areasproject.extensions.setupToolbar

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar(R.id.toolbar, getString(R.string.app_name), false)

        setupViewPagerTabs()
    }

    private fun setupViewPagerTabs() {
        container.offscreenPageLimit = 1
        container.adapter = TabsAdapter(context, supportFragmentManager)
        tabs.setupWithViewPager(container)

        val cor = ContextCompat.getColor(context, R.color.white)
        tabs.setTabTextColors(cor, cor)
    }
}
