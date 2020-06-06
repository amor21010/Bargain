package com.omaraboesmail.bargain.utils

import android.content.Context
import android.content.Intent
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.omaraboesmail.bargain.R


class NavigationFlow(context: Context) {
    private val mContext: Context = context
    fun navigateActivity(dest: AppCompatActivity, backable: Boolean = false) {
        val i = Intent(mContext, dest::class.java)
        if (!backable)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(i)
    }

    fun navigateToFragment(id: Int) {

        val fragment =
            (mContext as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        val navHostFragment = fragment as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(id)

    }

    fun onFragmentBackPressed(destinationFragmentId: Int) {
        val onBackPressedCallback =
            (mContext as AppCompatActivity).onBackPressedDispatcher.addCallback(mContext) {
                navigateToFragment(destinationFragmentId)
            }
        onBackPressedCallback.isEnabled = true
    }
}