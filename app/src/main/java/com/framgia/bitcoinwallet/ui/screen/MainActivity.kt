package com.framgia.bitcoinwallet.ui.screen

import android.content.Context
import android.content.Intent
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initComponents() {

    }

    override fun setEvents() {
        
    }

    companion object {

        fun getMainIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}
