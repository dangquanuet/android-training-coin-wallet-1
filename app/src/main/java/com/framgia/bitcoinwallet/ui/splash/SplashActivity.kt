package com.framgia.bitcoinwallet.ui.splash

import android.os.Handler
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity
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
    private fun checkLoginState(): Boolean {
        return SharedPreUtils.getLoginState(this)
    }

    /**
     * Get Intent from MainActivity and navigate to Home Screen
     */
    private fun startMainScreen() {

    }

    /**
     * Get Intent from LoginActivity and navigate to Login Screen
     */
    private fun startLoginScreen() {

    }

    companion object {
        private const val TAG = "SplashActivity"
        private const val WAITING_TIME: Long = 3000
    }
}
