package com.framgia.bitcoinwallet.ui.screen.wallet

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
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

class WalletActivity : BaseActivity(), BaseRecyclerViewHolder.OnItemClickListener<Wallet> {

    private lateinit var viewDataBinding: ActivityWalletBinding
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
        observeViewModel()

        viewDataBinding.viewModel?.notifyMessage?.observe(this,
                android.arch.lifecycle.Observer {
                    if (it != null) {
                        notify(it)
                    }
                })
    }

    override fun setEvents() {
        image_change_cur_wallet.setOnClickListener { viewDataBinding.viewModel?.changeCurrentWallet() }
        image_add_wallet.setOnClickListener { showEditDialog() }
    }

    override fun onItemClick(position: Int, data: Wallet) {

    }

    private fun initViewModel() {
        viewDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_wallet)
        viewDataBinding.apply {
            setLifecycleOwner(this@WalletActivity)
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

    private fun observeViewModel() {
        viewDataBinding.viewModel?.wallets?.observe(this, Observer {
            it?.let { recycler_wallet.adapter = WalletAdapter(this, it, this) }
        })

        viewDataBinding.viewModel?.newWalletAdded?.observe(this, Observer {
            it?.let { recycler_wallet.adapter.notifyItemInserted(recycler_wallet.adapter.itemCount) }
        })
    }

    private fun showEditDialog() {
        AlertDialog.Builder(this).apply {
            val edittext = EditText(this@WalletActivity)
            edittext.inputType = InputType.TYPE_CLASS_TEXT
            setMessage(getString(R.string.enter_wallet_name_title))
            setView(edittext)
            setPositiveButton(getString(R.string.ok_title)) { dialog, whichButton ->
                if (edittext.text.isNotEmpty()) {
                    viewDataBinding.viewModel?.addWallet(edittext.text.toString())
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
