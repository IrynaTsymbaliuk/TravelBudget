package com.example.travelbudget

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.travelbudget.basic.SharedPreference
import com.example.travelbudget.utils.LoginManager
import com.example.travelbudget.utils.TransformToCircleShape
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var header: View
    private lateinit var userName: TextView
    private lateinit var userMail: TextView
    private lateinit var userPhoto: ImageView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle


    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        header = navigationView.getHeaderView(0)
        userName = header.findViewById(R.id.user_name)
        userMail = header.findViewById(R.id.user_mail)
        userPhoto = header.findViewById(R.id.user_photo)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        val userObserver = Observer<FirebaseUser> { currentUser ->
            if (currentUser != null) {
                userName.text = currentUser.displayName
                userMail.text = currentUser.email
                userPhoto.visibility = View.VISIBLE
                Picasso.get().load(currentUser.photoUrl).transform(TransformToCircleShape()).into(userPhoto)
                navigationView.menu.setGroupVisible(R.id.accounts_for_unauthorized_users, false)
                navigationView.menu.setGroupVisible(R.id.accounts_for_authorized_users, true)
            } else {
                userName.text = ""
                userMail.text = ""
                userPhoto.visibility = View.INVISIBLE
                navigationView.menu.setGroupVisible(R.id.accounts_for_unauthorized_users, true)
                navigationView.menu.setGroupVisible(R.id.accounts_for_authorized_users, false)
                val sharedPreference = SharedPreference(this)
                if (sharedPreference.getSharedPreference("auth")) {
                    navController.navigate(R.id.add_trip)
                } else {
                    navController.navigate(R.id.auth)
                }
            }
        }
        LoginManager.instance.getCurrentUser().observe(this, userObserver)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sign_out -> {
                LoginManager.instance.signOut()
            }
            R.id.nav_change_account -> {
                LoginManager.instance.signOut()
                LoginManager.instance.signIn(this)
            }
            R.id.nav_sign_in -> {
                LoginManager.instance.signIn(this)
            }
            R.id.nav_feedback -> {
                sendFeedback()
            }
            R.id.nav_offer -> {
                makeOffer()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun sendFeedback() {
        val feedbackMail = "mailto:Iryna.Tsymbaliuk.dev@gmail.com" +
                "?subject=" + Uri.encode("Feedback")

        val feedbackIntent = Intent(Intent.ACTION_SENDTO)
        feedbackIntent.data = Uri.parse(feedbackMail)

        startActivity(Intent.createChooser(feedbackIntent, "Send a feedback"))

        try {
            startActivity(feedbackIntent)
        } catch (e: ActivityNotFoundException) {
            Log.w(TAG, "Feedback intent failed", e)
        }
    }

    private fun makeOffer() {
        val offerMail = "mailto:Iryna.Tsymbaliuk.dev@gmail.com" +
                "?subject=" + Uri.encode("Job offer")

        val offerIntent = Intent(Intent.ACTION_SENDTO)
        offerIntent.data = Uri.parse(offerMail)

        startActivity(Intent.createChooser(offerIntent, "Send a job offer"))

        try {
            startActivity(offerIntent)
        } catch (e: ActivityNotFoundException) {
            Log.w(TAG, "Offer intent failed", e)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LoginManager.instance.onResult(requestCode, resultCode, data, this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
