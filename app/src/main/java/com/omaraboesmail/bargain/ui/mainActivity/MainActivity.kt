package com.omaraboesmail.bargain.ui.mainActivity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.ui.SplashActivity
import com.omaraboesmail.bargain.utils.Const
import com.omaraboesmail.bargain.utils.DialogMaker
import com.omaraboesmail.bargain.utils.NavigationFlow
import com.omaraboesmail.bargain.utils.ToastMaker
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomAppBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var bottomNavController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var userEmail: TextView
    private lateinit var userName: TextView
    private lateinit var userImageView: ImageView
    private lateinit var badgeText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        createBottomNav()
        createDrawerLayout()

        DialogMaker.mContext = this


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        val mMenu = menu
        val badgeView = mMenu.findItem(R.id.action_cart).actionView
        badgeText = badgeView.findViewById(R.id.cartBadge)
        mainActivityViewModel.getCartSize().observe(this, Observer { cartSize ->
            badgeText.text = cartSize.toString()
            badgeText.visibility = View.VISIBLE

        })

        badgeView.setOnClickListener {
            NavigationFlow(this).navigateToFragment(R.id.nav_cart_fragment)
        }
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
                R.id.nav_customOrder
            )
        )
        setupActionBarWithNavController(bottomNavController, bottomAppBarConfiguration)
        bottomNavView.setupWithNavController(bottomNavController)
    }

    private fun createDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)

        userImageView = header.findViewById(R.id.userImage)
        userName = header.findViewById(R.id.userName)
        userEmail = header.findViewById(R.id.userEmail)

        mainActivityViewModel.getCurrantUserDetails().observe(this, Observer { user ->
            if (user != null) {
                if (user.photoUrl != "") Glide.with(this).load(user.photoUrl).into(userImageView)
                userEmail.text = user.email
                userName.text = user.name
                mainActivityViewModel.getOnlineCart(user.email).observe(this, Observer { cart ->
                    Log.d(Const.TAG, cart.toString())

                })

            }
        })


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_ordersHistory,
                R.id.nav_profile

            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home
                    , R.id.nav_store
                    , R.id.nav_individuals
                    , R.id.nav_customOrder
                    , R.id.nav_vegtables
                    , R.id.restaurantDetailsFragment
                    , R.id.homeServiceFragment
                    , R.id.homeStoreFragment
                    , R.id.handMadeFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavView.visibility = View.GONE
                    if (destination.id != R.id.nav_ordersHistory && destination.id != R.id.nav_profile)
                        toolbar.visibility = View.GONE

                }
            }
        }
    }

    override fun onBackPressed() {
        if ((navController.currentDestination?.id == R.id.nav_home)) {
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
        when (item.itemId) {
            R.id.action_logout -> {
                mainActivityViewModel.signOut()
                NavigationFlow(this).navigateActivity(SplashActivity())
            }
        }

        return super.onOptionsItemSelected(item)
    }


}


