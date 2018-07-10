package com.framgia.bitcoinwallet.ui.splash

import android.os.Handler
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.ui.screen.login.LoginActivity
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.SharedPreUtils

class SplashActivity : BaseActivity() {

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initComponents() {
        //do nothing
    }

    override fun setEvents() {
        Handler().postDelayed(Runnable {
            when (checkLoginState()) {
                true -> startMainScreen()
                else -> startLoginScreen()
            }
        }, WAITING_TIME)
    }

    /**
     * Check login state in SharedPreferences
     */
    private fun checkLoginState() = SharedPreUtils.getLoginState(this)

    /**
     * Get Intent from MainActivity and navigate to Home Screen
     */
    private fun startMainScreen() {
        startActivity(MainActivity.getMainIntent(this))
    }

    /**
     * Get Intent from LoginActivity and navigate to Login Screen
     */
    private fun startLoginScreen() {
        startActivity(LoginActivity.getLoginIntent(this))
    }

    companion object {
        private const val TAG = "SplashActivity"
        private const val WAITING_TIME: Long = 3000
    }
}
