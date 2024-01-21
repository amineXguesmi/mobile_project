package com.example.mobile_project.core.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import com.example.mobile_project.core.services.user.RegistrationRequestBody
import com.example.mobile_project.core.services.user.UserService
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserVm (private val userService: UserService = UserService()) : ViewModel() {
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()
    val loginErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val signupErrorLiveData: MutableLiveData<String> = MutableLiveData()
    var userEmail:String = ""
    var userPassword:String = ""
    var user:User? = null
    var userIsLogIn:Boolean = false
    private val PREF_NAME = "MyAppPrefs"
    fun setPassword(value: String) {
        userPassword = value
    }
    fun setEmail(value: String) {
        userEmail = value
    }
    fun loginUser(context: Context,email: String, password: String, role: String) {
        val requestBody = RegistrationRequestBody(email, password, "custom")

        val call: Call<User> = userService.loginUser(requestBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val result: User? = response.body()
                    user = result
                    if(user!=null){
                        saveUser(context, user = user!!)
                        loginResult.postValue(true)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    loginErrorLiveData.postValue(errorMessage)
                    loginResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                loginResult.postValue(false)
            }
        })
    }

    fun registerUser() {
        val requestBody = RegistrationRequestBody(userEmail, userPassword, "custom")

        val call: Call<UserRegister> = userService.registerUser(requestBody)

        call.enqueue(object : Callback<UserRegister> {
            override fun onResponse(call: Call<UserRegister>, response: Response<UserRegister>) {
                if (response.isSuccessful) {
                    val result: UserRegister? = response.body()
                    registrationResult.postValue(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    signupErrorLiveData.postValue(errorMessage)
                    Log.d("error", errorMessage)
                    registrationResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<UserRegister>, t: Throwable) {
                Log.d("error", "t7cha")
                registrationResult.postValue(false)
            }
        })
    }





    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            // Parse the JSON response and extract the "message" field
            val jsonError = JSONObject(errorBody)
            jsonError.getString("message")
        } catch (e: JSONException) {
            // Handle JSON parsing exception
            "An unexpected error occurred"
        }}

    fun saveUser(context: Context, user: User) {
        val gson = Gson()
        val json = gson.toJson(user.accessToken)
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("user_accessToken", json)
        editor.apply()
    }


    fun getUser(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("user_accessToken", null)
        val obj = gson.fromJson(json, String::class.java)
        if(obj != null) {
            userIsLogIn = true
        }
    }

    fun getUserById(id: String) :User?{
        var userId: User?=null
        val call: Call<User> = userService.getUserById(id)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val result: User? = response.body()
                    if (result != null) {
                        userId=result
                    }
                }

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
            }
        })
        return userId
    }
}