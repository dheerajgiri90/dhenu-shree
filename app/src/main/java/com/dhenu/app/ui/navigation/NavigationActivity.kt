package com.dhenu.app.ui.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityNavigationBinding
import com.dhenu.app.ui.account.AccountFragment
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.home.HomeFragment
import com.dhenu.app.util.CommonUtils

class NavigationActivity : BaseActivity<ActivityNavigationBinding, NavigationViewModel>(),
    NavigationNavigator {

    override val bindingVariable: Int
        get() = BR.navigationViewModel

    override val layoutId: Int
        get() = R.layout.activity_navigation

    override val viewModel = NavigationViewModel()

    private var doubleBackToExitPressedOnce = false
    private var runnable: Runnable? = null
    private lateinit var messageBadge: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor =
            ContextCompat.getColor(this@NavigationActivity, R.color.colorPrimary)
        viewModel.navigator = this

        init()
        setCurrentFragment(HomeFragment(), getString(R.string.home))
    }

    override fun init() {

        viewDataBinding!!.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            viewDataBinding!!.bottomNavigationView.menu.setGroupCheckable(0, true, true)
            when (menuItem.itemId) {

                R.id.navigation_home -> run {
                    setCurrentFragment(HomeFragment(), getString(R.string.home))

                }

                R.id.navigation_message -> run {
                    setCurrentFragment(HomeFragment(), getString(R.string.message))

                }

                R.id.navigation_account -> run {
                    setCurrentFragment(AccountFragment(), getString(R.string.account))
                }

            }
            true
        }

    }


    private fun setCurrentFragment(fragment: Fragment, tag: String) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 0) {
            val currentFragment = supportFragmentManager.fragments.last()
            if (currentFragment is HomeFragment) {
                viewDataBinding!!.bottomNavigationView.menu.setGroupCheckable(0, false, true)
                setCurrentFragment(HomeFragment(), getString(R.string.home))
            } else {
                askBeforeExit()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun askBeforeExit() {
        val handler = Handler(Looper.getMainLooper())
        if (!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true
            CommonUtils.showMessage(getString(R.string.want_to_exit), this)
            handler.postDelayed(Runnable { doubleBackToExitPressedOnce = false }
                .also { runnable = it }, 2000)
        } else {
            runnable?.let { handler.removeCallbacks(it) }
            finishAffinity()

        }
    }

}