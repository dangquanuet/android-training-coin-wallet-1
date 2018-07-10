package com.framgia.bitcoinwallet.ui.screen.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainTabAdapter(fm: FragmentManager,
                     private val fragments: HashMap<String, Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments.values.elementAt(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments.keys.elementAt(position)
    }
}
