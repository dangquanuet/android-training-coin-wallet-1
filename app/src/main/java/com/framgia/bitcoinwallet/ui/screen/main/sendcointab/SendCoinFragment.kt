package com.framgia.bitcoinwallet.ui.screen.main.sendcointab

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.view.View
import com.framgia.bitcoinwallet.databinding.FragmentSendCoinBinding
import kotlinx.android.synthetic.main.fragment_send_coin.view.*
import android.os.Build
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseFragment
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.obtainViewModel

class SendCoinFragment : BaseFragment<FragmentSendCoinBinding>(), SendCoinNavigator {

    companion object {
        fun newInstance() = SendCoinFragment()
        private const val TAG = "SendCoinFragment"
    }

    override fun getLayoutRes() = R.layout.fragment_send_coin

    override fun initData() {
        viewDataBinding.apply {
            viewModel = (activity as MainActivity).obtainViewModel(SendCoinViewModel::class.java)
            viewModel?.let { lifecycle.addObserver(it) }
        }
    }

    override fun startScanScreen() {

    }

    override fun observeModelData(view: View) {
        viewDataBinding?.viewModel?.showAlertDialog?.observe(this, Observer {
            if (it != null && it) {
                showVerifySendDialog(view)
            }
        })

        viewDataBinding?.viewModel?.sendCoinState?.observe(this, Observer {
            if (it != null && it) {
                notifyMessage(getString(R.string.send_coin_successed))
            } else {
                notifyMessage(getString(R.string.send_coin_failed))
            }
        })
    }

    override fun setEvents(view: View) {
        view.button_send_coin.setOnClickListener {
            viewDataBinding?.viewModel?.verifySendCoin(view.edit_bitcoin_add.text.toString(),
                    view.edit_amount.text.toString())
        }

        view.image_scan.setOnClickListener { startScanScreen() }
    }

    private fun showVerifySendDialog(view: View) {
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(context)
        }
        builder.setTitle(getString(R.string.dialog_send_title))
                .setMessage(getString(R.string.dialog_send_message))
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    viewDataBinding?.viewModel?.showAlertDialog?.value = false
                    viewDataBinding?.viewModel?.sendCoin(view.edit_bitcoin_add.text.toString(),
                            view.edit_amount.text.toString(), view.edit_note.text.toString())
                }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    viewDataBinding?.viewModel?.showAlertDialog?.value = false
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

    }

    private fun notifyMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}
