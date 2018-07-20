package com.framgia.bitcoinwallet.ui.screen.changepassws

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.databinding.ActivityChangePasswdBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_change_passwd.*

class ChangePassWdActivity : BaseActivity<ActivityChangePasswdBinding>() {

    companion object {
        private const val TAG = "ChangePassWdActivity"
        fun getChangePassWdIntent(context: Context) = Intent(context, ChangePassWdActivity::class.java)
    }

    override fun navigateLayout() = false

    override fun getLayoutRes() = R.layout.activity_change_passwd

    override fun initComponents() {
        initViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = this@ChangePassWdActivity.obtainViewModel(ChangePassWdViewModel::class.java)
                .apply {
                    lifecycle.addObserver(this)
                }
    }

    override fun setEvents() {
        button_change_confirm.setOnClickListener { changePassWord() }
    }

    override fun observeViewModel() {
        binding.viewModel?.notifyMessage?.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            if (it == getString(R.string.change_passwd_successed)) finish()
        })
    }

    private fun changePassWord() {
        binding.viewModel?.changePassWord(edit_email.text.toString(),
                edit_passwd.text.toString(), edit_new_passwd.text.toString())
    }
}
