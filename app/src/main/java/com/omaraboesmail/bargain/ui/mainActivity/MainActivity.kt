package com.omaraboesmail.bargain.ui.mainActivity

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.ui.SplashActivity
import com.omaraboesmail.bargain.utils.DialogMaker.mContext
import com.omaraboesmail.bargain.utils.NavigationFlow
import com.omaraboesmail.bargain.utils.ToastMaker
import kotlinx.android.synthetic.main.app_bar_main.*


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

    val TAG = "ooooooo"
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        createBottomNav()
        createDrawerLayout()
        mContext = this


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
                toolbar.visibility = View.VISIBLE
                bottomNavView.visibility = View.VISIBLE
            } else {
                bottomNavView.visibility = View.GONE
                if (destination.id != R.id.nav_myOrders && destination.id != R.id.nav_profile)
                    toolbar.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if ((navController.currentDestination!!.id == R.id.nav_home)) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity()
                return
            }
            this.doubleBackToExitPressedOnce = true
            ToastMaker(
                this,
                "Please click BACK again to exit"
            )
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            userDataViewModel.signOut()

            NavigationFlow(this).navigateActivity(SplashActivity())
        }
        return super.onOptionsItemSelected(item)
    }


}


