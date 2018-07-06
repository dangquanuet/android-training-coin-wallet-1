package com.framgia.bitcoinwallet.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreUtils {

    fun getLoginState(context: Context): Boolean {
        var sharedPref: SharedPreferences =
                context.getSharedPreferences(Constant.PREF_AUTHENCIATION, MODE_PRIVATE)
        return sharedPref?.getBoolean(Constant.PREF_LOGIN_STATE_KEY, false)
    }

    fun changeLoginState(context: Context, state: Boolean) {
        var editor: SharedPreferences.Editor =
                context.getSharedPreferences(Constant.PREF_AUTHENCIATION, MODE_PRIVATE).edit()

        with(editor) {
            putBoolean(Constant.PREF_LOGIN_STATE_KEY, state)
            commit()
        }
    }
}
