package com.framgia.bitcoinwallet.ui.screen.login

import android.content.Context
import android.content.Intent
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity

class LoginActivity : BaseActivity() {
    override fun navigateLayout(): Boolean = false

    override fun getLayoutRes(): Int = R.layout.activity_login

    override fun initComponents() {
    }

    override fun setEvents() {
    }

    companion object {

        fun getLoginIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }
}