package com.framgia.bitcoinwallet.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created: 05/07/2018
 * By: Sang
 * Description:
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponentsOnCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View? = inflater.inflate(getLayoutRes(), container, false)
        rootView?.let { initComponentsOnCreateView(it, savedInstanceState) }
        return rootView
    }

    abstract fun initComponentsOnCreate(savedInstanceState: Bundle?)

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initComponentsOnCreateView(rootView: View, savedInstanceState: Bundle?)
}
