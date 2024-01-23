package com.example.mobile_project

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.ui.screens.HomePage
import com.example.mobile_project.ui.screens.LoginScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isInternetAvailable(this)) {
            viewModel.getUser(this)
            handleUserLogin()
        } else {
            showNoInternetDialog()
        }
    }

    private fun handleUserLogin() {
        if (viewModel.userIsLogIn) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private val viewModel: UserVm by viewModels() {
        UserVmFactory()
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }


    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.internet_dialog, null)
        builder.setView(view)

        val retryButton=view.findViewById<TextView>(R.id.try_again)
        retryButton.setOnClickListener { recreate() }
        builder.create().show()
    }
}
