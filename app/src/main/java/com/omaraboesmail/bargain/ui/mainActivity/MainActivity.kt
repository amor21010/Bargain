package com.omaraboesmail.bargain.ui.mainActivity

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.omaraboesmail.bargain.NavigationFlow
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.ToastMaker
import com.omaraboesmail.bargain.pojo.UserVerState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.fbUser
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.firebaseAuth
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.isUserVerified
import com.omaraboesmail.bargain.singiltons.UserDB.currant
import com.omaraboesmail.bargain.ui.SplashActivity


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private val userDataViewModel: UserDataViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomAppBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var bottomNavController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        createBottomNav()
        createDrawerLayout()

        currant.observe(this, Observer {
            userDataViewModel.isUserEmailVerified().observe(this, Observer { boolean ->
                if (fbUser != null)
                    ToastMaker(this, fbUser!!.isEmailVerified.toString())
                if (boolean) {
                    isUserVerified.value = UserVerState.VERIFIED
                    if (it.approved != boolean) {
                        it.approved = boolean
                        userDataViewModel.updateUser(user = it)
                    }
                    // userDataViewModel.updateUser(it)
                }
            })
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || navController.navigateUp(
            bottomAppBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    private fun createBottomNav() {
        bottomNavView = findViewById(R.id.Bottom_nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        bottomNavController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        bottomAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_store,
                R.id.nav_individuals,
                R.id.nav_order
            )
        )
        setupActionBarWithNavController(bottomNavController, bottomAppBarConfiguration)
        bottomNavView.setupWithNavController(bottomNavController)
    }

    private fun createDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_myOrders,
                R.id.nav_profile

            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_home
                || destination.id == R.id.nav_store
                || destination.id == R.id.nav_individuals
                || destination.id == R.id.nav_order
            ) {
                bottomNavView.visibility = View.VISIBLE
            } else {
                bottomNavView.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else {
            if ((navController.currentDestination!!.id == R.id.nav_home)) {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity()
                    return
                }
                this.doubleBackToExitPressedOnce = true
                ToastMaker(this, "Please click BACK again to exit")
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

            } else {
                navController.navigate(R.id.nav_home)
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {

            firebaseAuth.addAuthStateListener {
                if (it.currentUser != null) userDataViewModel.signOut()
                else {
                    NavigationFlow(this).navigateActivity(SplashActivity())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


