package com.framgia.bitcoinwallet.ui.screen.forgotpasswd

import android.content.Context
import android.content.Intent
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import com.framgia.bitcoinwallet.databinding.ActivityForgotPasswdBinding
import com.framgia.bitcoinwallet.ui.screen.login.LoginActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_forgot_passwd.*

class ForgotPassWdActivity : BaseActivity<ActivityForgotPasswdBinding>(), ForgotPassWdNavigator {

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_forgot_passwd
    }

    override fun initComponents() {
        setUpWindowBackGround()
        initViewModel()
    }

    override fun observeViewModel() {
        binding.viewModel?.notifyMessage?.observe(this,
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
        startActivity(LoginActivity.getLoginIntent(this))
    }

    private fun sendMail() {
        binding.viewModel?.sendEmail(edit_email.text?.toString() ?: "")
    }

    private fun retrySendVerify() {
        binding.viewModel?.retryVerify(edit_email.text?.toString() ?: "")
    }

    private fun initViewModel() {
        binding.viewModel = this.obtainViewModel(ForgotPassWdViewModel::class.java)
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

    companion object {
        fun getForgotPassWdIntent(context: Context)
                = Intent(context, ForgotPassWdActivity::class.java)
    }
}
