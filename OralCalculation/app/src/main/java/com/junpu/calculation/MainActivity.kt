package com.junpu.calculation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
        fragment as NavHostFragment
        fragment.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar?.setupWithNavController(navController)
        toolbar?.setNavigationOnClickListener(navigationListener)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.prepareFragment) {
            finish()
        } else {
            navigationListener(null)
        }
    }

    private val navigationListener = { _: View? ->
        if (navController.currentDestination?.id == R.id.oralFragment) {
            AlertDialog.Builder(this)
                .setMessage(R.string.alert_msg)
                .setPositiveButton(R.string.sure) { _, _ -> navController.navigateUp() }
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show()
        } else {
            navController.navigate(R.id.prepareFragment)
        }
    }

}