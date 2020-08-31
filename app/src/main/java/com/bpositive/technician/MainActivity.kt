package com.bpositive.technician

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bpositive.R
import com.bpositive.technician.utils.FragmentListener
import com.bpositive.technician.utils.toast
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), FragmentListener,
    NavigationView.OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {

    var titleName: String? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = ""
        collapsingToolbar.isTitleEnabled = false

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open_msg, R.string.close_msg
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        toggle.isDrawerIndicatorEnabled = true

        setActionBarViews()


        navigationView.setNavigationItemSelectedListener(this)
        appBarLayout.addOnOffsetChangedListener(this)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFrag) as NavHostFragment
        val navController = host.navController

        NavigationUI.setupWithNavController(navigationView, navController)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        navigationView.setupWithNavController(navController)


        navHostFrag.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            when(findNavController(v?.id!!).currentDestination?.id){
                R.id.homeFragment -> {
                    mainHomeHeaderImgLayout?.visibility = View.VISIBLE
                    mainDomainHeaderImgLayout?.visibility = View.GONE
                }
                else -> {
                    mainHomeHeaderImgLayout?.visibility = View.GONE
                    mainDomainHeaderImgLayout?.visibility = View.VISIBLE
                }
            }
        }

    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main)

        setupNavigation()

    }

    private fun setupNavigation() {

        setSupportActionBar(toolbar);

        toolbar.title = ""
        //  collapsingToolbar.isTitleEnabled = false

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFrag) as NavHostFragment
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener(this);
        appBarLayout.addOnOffsetChangedListener(this)

        /*navController.addOnDestinationChangedListener { _, destination, _ ->  //3
            if (destination.id in arrayOf(
                    R.id.splashFragment,
                    R.id.languageFragment,

                    R.id.loginFragment
                )
            ) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }

            *//*if (destination.id == R.id.presentationFragment) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }*//*
        }*/

        navHostFrag.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            when (findNavController(v?.id!!).currentDestination?.id) {
                R.id.homeFragment -> {
                    /*mainHomeHeaderImgLayout?.visibility = View.VISIBLE
                    mainDomainHeaderImgLayout?.visibility = View.GONE
                    headerImages?.visibility = View.GONE*/
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                else -> {
                    /*mainDomainHeaderImgLayout?.visibility = View.VISIBLE
                    headerImages?.visibility = View.VISIBLE
                    mainHomeHeaderImgLayout?.visibility = View.GONE*/
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

//                    setHeaderImage()
                }
            }
        }

    }

    /*private fun setActionBarViews() {
        // actionbar
        val actionBar = supportActionBar
        //set back button
        actionBar.run {
            this!!.title = titleName
            //set back button
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }
    }*/


    override fun setTitle(title: String) {
        Log.v("title", "My title: $title")
        titleName = title
    }

    override fun setShowHomeViews(home: Boolean) {

        Log.i("showHomeViews", "showHomeViews $home")
        /*if (collapsingToolbar != null) {
            if (home)
                collapsingToolbar.visibility = View.VISIBLE
            else
                collapsingToolbar.visibility = View.GONE
        }*/

//        handleHomeDomainImage()


    }

    private fun setupViewFragment() {
//        replaceFragmentInActivity(SplashFragment.newInstanceTest(), R.id.fragment_container, "")
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            when (findNavController(R.id.navHostFrag).currentDestination?.id) {
                R.id.homeDetailFragment -> findNavController(
                    R.id.navHostFrag
                ).navigate(R.id.homeFragment)

                R.id.homeFragment -> finish()

                else -> super.onBackPressed()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFrag)
        when (findNavController(R.id.navHostFrag).currentDestination?.id) {
            R.id.homeDetailFragment -> {
                findNavController(R.id.navHostFrag).navigate(
                    R.id.homeFragment
                )
                return false
            }
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()

//        onBackPressed()
//        return true

//        findNavController(R.id.navHostFrag).navigateUp(drawerLayout)

        drawerLayout.openDrawer(navigationView, true)
//
        return true
    }*/


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        println("onNavigationItemSelected")

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFrag) as NavHostFragment
        val navController = host.navController

        when (item.itemId) {
            R.id.settingsFragment -> {
                navController.navigate(R.id.settingsFragment)
            }
            R.id.loginFragment -> {
                navController.navigate(R.id.loginFragment)
            }
            R.id.menu_my_works -> {
                navController.navigate(R.id.myWorkFragment)
            }
            R.id.myProfileFragment -> {
                navController.navigate(R.id.myProfileFragment)
            }
            else -> {
                toast("Coming soon")
            }
//            R.id.loginFragment -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            R.id.menu_contact_us -> Log.i("onNavigationItemSelected id", "menu_contact_us")
//            R.id.menu_about_us -> ShowToast("Coming soon")
        }

        item.isCheckable = true
        drawerLayout.closeDrawers()

//        drawerLayout?.closeDrawer(GravityCompat.START)
        return true

    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        runOnUiThread {
            //scroll
            var scrollRange = -1

            Log.i("onOffsetChanged", "onOffsetChanged called")

            if (scrollRange == -1) {
                scrollRange = appBarLayout?.totalScrollRange!!
            }

            if (scrollRange + verticalOffset == 0) {
                toolbar.title = titleName
            } else {
                toolbar.title = ""
            }
        }

    }

    fun setHeaderImage(imgUrl: String?, title: String?) {
        /*userImage.background = null
        userImage.loadImage(imgUrl!!)*/
    }

    fun removeScrollingBehaviour() {
        val params = navHostFrag.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = null
    }

    fun enableScrollingBehaviour() {
        val params = navHostFrag.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        //  navHostFrag.requestLayout()
    }

}
