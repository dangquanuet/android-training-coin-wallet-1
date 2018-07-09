package com.framgia.bitcoinwallet.util

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import com.framgia.bitcoinwallet.ViewModelFactory

/**
 * Created: 06/07/2018
 * By: Sang
 * Description:
 */
fun AppCompatActivity.setUpActionBar(toolBar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolBar)
    supportActionBar?.run { action() }
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application))
                .get(viewModelClass)

