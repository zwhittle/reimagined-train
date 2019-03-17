package com.awesome.zach.jotunheimrsandbox.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.awesome.zach.jotunheimrsandbox.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),
    View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient

//    private val profilePicRetriever = ProfilePicRetriever()

//    private val callback = object: Callback<Drawable> {
//        override fun onFailure(call: Call<Drawable>, t: Throwable) {
//            Log.e("LoginActivity", "Problem getting profile pic")
//        }
//
//        override fun onResponse(call: Call<Drawable>, response: Response<Drawable>) {
//            response?.isSuccessful.let {
//
//            }
//        }
//    }

    companion object {
        const val RC_SIGN_IN = 69
        const val LOG_TAG = "LoginActivity"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,
                               RC_SIGN_IN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this,
                                               LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity,
                                              Observer {
                                                  val loginState = it ?: return@Observer
                                              })

        loginViewModel.loginResult.observe(this@LoginActivity,
                                           Observer {
                                               val loginResult = it ?: return@Observer

                                               loading.visibility = View.GONE
                                               if (loginResult.error != null) {
                                                   showLoginFailed(loginResult.error)
                                               }
                                               if (loginResult.success != null) {
                                                   updateUiWithUser(loginResult.success)
                                               }
                                               setResult(Activity.RESULT_OK)

                                               //Complete and destroy login activity once successful
                                               finish()
                                           })

        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        sign_in_button.setOnClickListener(this)

        tvLoginUser.text = getString(R.string.no_one)

        Picasso.get().load(R.drawable.ic_launcher_background).into(ivSignIn)

        // Configure sign-in to request the user's ID, email address, and basic profile.
        // ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this,
                                                     gso)
    }

    override fun onStart() {
        super.onStart()

        // Check for existing Google Sign In account,
        // if the user is already signed in the GoogleSignInAccount will be null
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode,
                               resultCode,
                               data)

        // Result return from launching the Intent from GoogleSignInClient.signInIntent
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.result

            // Signed in successfully, show authenticated UI

            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason
            // Please refer to the GoogleSignInStatusCodes class reference for more information
            Log.w(LOG_TAG,
                  "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val displayName = account.displayName
            val image = account.photoUrl
            val userView = LoggedInUserView(displayName!!, image)
            updateUiWithUser(userView)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        val imageUri =  model.image

        tvLoginUser.text = displayName

        val iv = findViewById<ImageView>(R.id.ivSignIn)

        Picasso.get().load(imageUri).into(iv)
//        ivSignIn.setImageDrawable(image)

        // TODO : initiate successful logged in experience
        Toast.makeText(applicationContext,
                       "$welcome $displayName",
                       Toast.LENGTH_LONG)
            .show()
    }

//    private fun loadImageFromUrl(imageUri: Uri?): Drawable? {
//         if (imageUri == null) {
//             return null
//         } else {
//             return try {
//                 if (isNetworkConnected()) {
//                     return profilePicRetriever.getProfilePic(callback)
//                 } else {
//                     AlertDialog.Builder(this).setTitle("No Internet Connection")
//                         .setMessage("Please check your internet connection and try again")
//                         .setPositiveButton(android.R.string.ok) { _, _ -> }
//                         .setIcon(android.R.drawable.ic_dialog_alert).show()
//                 }
//             } catch (e: Exception) {
//                 Log.e(LOG_TAG, e.toString(), e)
//                 null
//             }
//         }
//
//        return null
//    }

    fun signOut(view: View) {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener { updateUIForSignOut() }
    }

    private fun updateUIForSignOut() {
        tvLoginUser.text = getString(R.string.no_one)
        Picasso.get().load(R.drawable.ic_launcher_background).into(ivSignIn)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showLoginFailed(
        @StringRes
        errorString: Int) {
        Toast.makeText(applicationContext,
                       errorString,
                       Toast.LENGTH_SHORT)
            .show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence,
                                       start: Int,
                                       count: Int,
                                       after: Int) {
        }

        override fun onTextChanged(s: CharSequence,
                                   start: Int,
                                   before: Int,
                                   count: Int) {
        }
    })
}
