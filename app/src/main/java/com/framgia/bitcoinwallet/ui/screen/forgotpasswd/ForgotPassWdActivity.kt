package com.framgia.bitcoinwallet.ui.screen.forgotpasswd

import android.databinding.DataBindingUtil
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import com.framgia.bitcoinwallet.databinding.ActivityForgotPasswdBinding
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_forgot_passwd.*

class ForgotPassWdActivity : BaseActivity(), ForgotPassWdNavigator {

    private lateinit var viewDataBinding: ActivityForgotPasswdBinding


    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_forgot_passwd
    }

    override fun initComponents() {
        setUpWindowBackGround()
        initViewModel()

        viewDataBinding.viewModel?.notifyMessage?.observe(this,
                android.arch.lifecycle.Observer {
                    if (it != null) {
                        notify(it)
                    }
                })
    }

    override fun setEvents() {

        button_back.setOnClickListener { finishScreen() }

        button_send_mail.setOnClickListener { sendMail() }

        button_login.setOnClickListener { startLogin() }

        text_retry.setOnClickListener { retrySendVerify() }

    }

    override fun finishScreen() {
        finish()
    }

    override fun startLogin() {

    }

    private fun sendMail() {
        viewDataBinding.viewModel?.sendEmail(edit_email.text?.toString() ?: "")
    }

    private fun retrySendVerify() {
        viewDataBinding.viewModel?.retryVerify(edit_email.text?.toString() ?: "")
    }

    private fun initViewModel() {
        viewDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_forgot_passwd)
        viewDataBinding.setLifecycleOwner(this)
        viewDataBinding.viewModel = this.obtainViewModel(ForgotPassWdViewModel::class.java)
    }

    /**
     * Config background for status bar to gradient color
     */
    private fun setUpWindowBackGround() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.getWindow()
            val background = this.getResources().getDrawable(R.drawable.shape_status_bar)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent))
            window.setNavigationBarColor(this.getResources().getColor(android.R.color.transparent))
            window.setBackgroundDrawable(background)
        }
    }

    private fun notify(information: String) {
        Toast.makeText(this, information, Toast.LENGTH_SHORT).show()
    }
}
