package com.framgia.bitcoinwallet.util

import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

/**
 * Created: 06/07/2018
 * By: Sang
 * Description:
 */
fun AppCompatActivity.setUpActionBar(toolBar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolBar)
    supportActionBar?.run { action() }
}
