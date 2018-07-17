package com.framgia.bitcoinwallet.ui.screen.wallet

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ActivityWalletBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder
import com.framgia.bitcoinwallet.util.obtainViewModel
import com.framgia.bitcoinwallet.util.setUpActionBar
import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity<ActivityWalletBinding>(), BaseRecyclerViewHolder.OnItemClickListener<Wallet> {

    private lateinit var toolBar: Toolbar

    companion object {
        const val TAG = "WalletActivity"
        fun getWalletActivityIntent(context: Context): Intent
                = Intent(context, WalletActivity::class.java)
    }

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_wallet
    }

    override fun initComponents() {
        setUpToolbar()
        initViewModel()
    }

    override fun setEvents() {
        image_change_cur_wallet.setOnClickListener { binding.viewModel?.changeCurrentWallet() }
        image_add_wallet.setOnClickListener { showEditDialog() }
    }

    override fun observeViewModel() {
        binding.viewModel?.wallets?.observe(this, Observer {
            it?.let { recycler_wallet.adapter = WalletAdapter( it, this) }
        })

        binding.viewModel?.newWalletAdded?.observe(this, Observer {
            it?.let { recycler_wallet.adapter.notifyItemInserted(recycler_wallet.adapter.itemCount) }
        })

        binding.viewModel?.notifyMessage?.observe(this,
                android.arch.lifecycle.Observer {
                    if (it != null) {
                        notify(it)
                    }
                })
    }

    override fun onItemClick(position: Int, data: Wallet) {

    }

    private fun initViewModel() {
        binding.apply {
            viewModel = this@WalletActivity.obtainViewModel(WalletViewModel::class.java)
            viewModel?.let { lifecycle.addObserver(it) }
        }
    }

    private fun setUpToolbar() {
        toolBar = findViewById(R.id.toolbar_wallet)
        setUpActionBar(toolBar) {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showEditDialog() {
        AlertDialog.Builder(this).apply {
            val edittext = EditText(this@WalletActivity)
            edittext.inputType = InputType.TYPE_CLASS_TEXT
            setMessage(getString(R.string.enter_wallet_name_title))
            setView(edittext)
            setPositiveButton(getString(R.string.ok_title)) { dialog, whichButton ->
                if (edittext.text.isNotEmpty()) {
                    binding.viewModel?.addWallet(edittext.text.toString())
                }
            }
            setNegativeButton(getString(R.string.cancel_title)) { dialog, whichButton ->

            }
        }.show()
    }

    private fun notify(information: String) {
        Toast.makeText(this, information, Toast.LENGTH_SHORT).show()
    }
}
