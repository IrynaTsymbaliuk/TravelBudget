package com.example.travelbudget.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.travelbudget.R
import com.example.travelbudget.basic.App
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginManager() {

    private var auth: FirebaseAuth
    private var googleSignInClient: GoogleSignInClient
    private val currentUser = MutableLiveData<FirebaseUser>()

    companion object {

        val instance = LoginManager()

        private const val TAG = "LoginManager"
        private const val RC_SIGN_IN = 9001

    }

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(R.string.default_web_client_id.toString())
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(App.instance, gso)

        auth = FirebaseAuth.getInstance()

        currentUser.value = auth.currentUser
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return currentUser
    }

    fun signIn(activity: Activity) {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity) {

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!, activity)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                currentUser.value = auth.currentUser
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, activity: Activity) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    currentUser.value = auth.currentUser
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    currentUser.value = auth.currentUser
                }

            }
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        currentUser.value = auth.currentUser
    }


}