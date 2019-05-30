package com.example.travelbudget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.auth_fragment.view.*
import kotlinx.android.synthetic.main.nav_header.nav_header as nav_header1
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class AuthFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.google_signinbutton ->
                LoginManager.instance.signIn(activity!!)
        }
    }

    private lateinit var navigationView: NavigationView
    private lateinit var header: View
    private lateinit var userName: TextView
    private lateinit var userMail: TextView
    private lateinit var userPhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.auth_fragment, container, false)

        rootView.google_signinbutton.setOnClickListener(this)

        navigationView = activity!!.findViewById(R.id.nav_view)
        header = navigationView.getHeaderView(0)
        userName = header.findViewById(R.id.user_name)
        userMail = header.findViewById(R.id.user_mail)
        userPhoto = header.findViewById(R.id.user_photo)

        val userObserver = Observer<FirebaseUser> { currentUser ->
            if (currentUser != null) {
                findNavController().navigate(R.id.emptyTripCardFragment)
            }
        }

        rootView.skip_textview.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.emptyTripCardFragment))

        LoginManager.instance.getCurrentUser().observe(this, userObserver)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }


}

