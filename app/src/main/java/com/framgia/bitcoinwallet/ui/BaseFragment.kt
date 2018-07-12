package com.framgia.bitcoinwallet.ui

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<ViewBinding : ViewDataBinding> : Fragment(), LifecycleOwner{
    lateinit var viewDataBinding: ViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewDataBinding .apply {
            setLifecycleOwner(this@BaseFragment)
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setEvents(viewDataBinding.root)
        observeModelData(viewDataBinding.root)
        retainInstance = true
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initData()

    abstract fun observeModelData(view: View)

    abstract fun setEvents(view: View)
}
