package com.framgia.bitcoinwallet.ui.screen

import android.content.Context
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.util.setUpActionBar

class MainActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolBar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initComponents() {
        setUpToolbar()
        setUpNavigationDrawer()
    }

    private fun setUpToolbar() {
        toolBar = findViewById(R.id.toolbar_main)
        setUpActionBar(toolBar) {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpNavigationDrawer() {
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_main).apply {
            setStatusBarBackground(R.color.color_deep_purple_50_700)
        }
        drawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open_drawer,
                R.string.close_drawer
        )
        navigationView = findViewById(R.id.navigation_view_main)
        drawerToggle.syncState()
    }

    override fun setEvents() {
        drawerLayout.addDrawerListener(drawerToggle)
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {

            }
            item.isChecked = true
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_pr_code ->
                return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun getMainIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}
