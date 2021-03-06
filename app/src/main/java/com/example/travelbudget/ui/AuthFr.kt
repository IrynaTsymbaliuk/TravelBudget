package com.example.travelbudget.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelbudget.R
import com.example.travelbudget.basic.SharedPreference
import com.example.travelbudget.utils.LoginManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.auth_fragment.view.*

class AuthFr : Fragment(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.google_signinbutton -> {
                LoginManager.instance.signIn(activity!!)
                val sharedPreference = SharedPreference(activity!!)
                sharedPreference.setSharedPreference("auth", true)
            }
            R.id.skip_textview -> {
                findNavController().navigate(R.id.view_pager)
                val sharedPreference = SharedPreference(activity!!)
                sharedPreference.setSharedPreference("auth", true)
            }
        }
    }

    private lateinit var navigationView: NavigationView
    private lateinit var header: View
    private lateinit var userName: TextView
    private lateinit var userMail: TextView
    private lateinit var userPhoto: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.auth_fragment, container, false)

        rootView.google_signinbutton.setOnClickListener(this)
        rootView.skip_textview.setOnClickListener(this)

        navigationView = activity!!.findViewById(R.id.nav_view)
        header = navigationView.getHeaderView(0)
        userName = header.findViewById(R.id.user_name)
        userMail = header.findViewById(R.id.user_mail)
        userPhoto = header.findViewById(R.id.user_photo)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPreference(activity!!).getSharedPreference("auth")) {
            findNavController().navigate(AuthFrDirections.actionAuthToPagerFragment())
        }
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