package com.framgia.bitcoinwallet.ui.screen.wallet

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ActivityWalletBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder
import com.framgia.bitcoinwallet.ui.screen.detailswallet.DetailsWalletActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import com.framgia.bitcoinwallet.util.setUpActionBar
import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity<ActivityWalletBinding>(),
        BaseRecyclerViewHolder.OnItemClickListener<Wallet>, WalletNavigator {

    private lateinit var toolBar: Toolbar
    private var currentWalletChoosed: Int = -1
    private var isChangeClick: Boolean = false
    private var isAllowChange: Boolean = true
    private var walletAdapter: WalletAdapter? = null

    companion object {
        const val TAG = "WalletActivity"
        fun getWalletActivityIntent(context: Context): Intent = Intent(context, WalletActivity::class.java)
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

        if (walletAdapter == null) {
            walletAdapter = WalletAdapter(mutableListOf(), this)
            recycler_wallet.adapter = walletAdapter
        }
    }

    override fun setEvents() {
        image_add_wallet.setOnClickListener { showEditDialog() }
        image_change_cur_wallet.setOnClickListener { showRadioButtonChoose() }
        button_change_wallet.setOnClickListener { showRadioButtonChoose() }
        button_ok.setOnClickListener { changeWallet() }
        toolbar_wallet.setNavigationOnClickListener { finish() }
        image_cur_wallet_show.setOnClickListener {
            binding.viewModel?.currentWallet?.value?.let {
                goWalletDetailsScreen(it)
            }
        }
    }

    override fun observeViewModel() {
        binding.viewModel?.newWalletAdded?.observe(this, Observer {
            it?.let { walletAdapter?.updateAddWallet(it) }
        })

        binding.viewModel?.notifyMessage?.observe(this,
                android.arch.lifecycle.Observer {
                    if (it != null) {
                        notify(it)
                    }
                })
    }

    override fun onItemClick(position: Int, data: Wallet) {
        if (isChangeClick) { //case check item
            walletAdapter?.notifyPreviousItemCheck(position)
            currentWalletChoosed = position
            isAllowChange = data.isChoosed
        } else { //case open details screen
            goWalletDetailsScreen(data)
        }
    }

    override fun goWalletDetailsScreen(item: Wallet) {
        startActivity(DetailsWalletActivity.getDetailsWallet(this, item))
    }

    private fun initViewModel() {
        binding.viewModel = this@WalletActivity.obtainViewModel(WalletViewModel::class.java).apply {
            lifecycle.addObserver(this)
        }
    }

    private fun setUpToolbar() {
        toolBar = findViewById(R.id.toolbar_wallet)
        setUpActionBar(toolBar) {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showRadioButtonChoose() {
        isChangeClick = true
        binding.viewModel?.isChangeWalletClick?.value = true
        walletAdapter?.showCheckBoxChoose(true)
    }

    private fun changeWallet() {
        isChangeClick = false
        binding.viewModel?.isChangeWalletClick?.value = false
        walletAdapter?.showCheckBoxChoose(false)
        if (currentWalletChoosed != -1 && isAllowChange) {
            binding.viewModel?.changeWallet(currentWalletChoosed)
            currentWalletChoosed = -1
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
