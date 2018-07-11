package com.framgia.bitcoinwallet.ui.screen.signup

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.databinding.ActivitySingUpBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.ui.screen.login.LoginActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_sing_up.*

class SignUpActivity : BaseActivity<ActivitySingUpBinding>(), SignUpActionListener {

    override fun clickRegister(view: View) {
        var user = User(email = edt_email.getText().toString(),
                fullName = edt_full_name.getText().toString(),
                password = edt_pass.getText().toString(),
                phoneNumber = edt_phone.getText().toString(),
                country = edt_country.getText().toString())
        binding.viewModel?.user?.value = user
        binding.viewModel?.rePassword?.value = edt_confirm_pass.getText().toString()
        binding.viewModel?.onRegister()
    }

    override fun clickSigIn() {
        startActivity(LoginActivity.getLoginIntent(this))
        finish()
    }

    override fun navigateLayout(): Boolean = false

    override fun getLayoutRes(): Int = R.layout.activity_sing_up

    override fun initComponents() {
//        binding.viewModel = this@SignUpActivity.obtainViewModel(SignUpViewModel::class.java)
        binding.apply {
            viewModel = this@SignUpActivity.obtainViewModel(SignUpViewModel::class.java)
            listener = this@SignUpActivity
            setLifecycleOwner(this@SignUpActivity)
        }
    }

    override fun setEvents() {
    }

    override fun observeViewModel() {
        binding.viewModel?.notifyMessage?.observe(this, Observer {
            it?.let { it1 -> notify(it1) }
        })
    }

    private fun notify(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getSignUpIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }
}
