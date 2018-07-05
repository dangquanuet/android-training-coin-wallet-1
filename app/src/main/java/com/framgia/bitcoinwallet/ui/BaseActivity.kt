package com.framgia.bitcoinwallet.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Created: 05/07/2018
 * By: Sang
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (navigateLayout()) {
            return
        }
        setContentView(getLayoutRes())
        initComponents()
        setEvents()
    }

    abstract fun navigateLayout(): Boolean

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initComponents()

    abstract fun setEvents()
}
