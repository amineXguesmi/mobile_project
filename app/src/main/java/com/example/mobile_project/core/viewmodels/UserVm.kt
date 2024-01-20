package com.example.mobile_project.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import com.example.mobile_project.core.services.RegistrationRequestBody
import com.example.mobile_project.core.services.UserService
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserVm (private val userService: UserService= UserService()) : ViewModel() {
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()
    val loginErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val signupErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private var userEmail:String = ""
    private var userPassword:String = ""
    fun setPassword(value: String) {
        userPassword = value
    }
    fun setEmail(value: String) {
        userEmail = value
    }
    fun loginUser(email: String, password: String, role: String) {
        val requestBody = RegistrationRequestBody(email, password, "custom")

        val call: Call<User> = userService.loginUser(requestBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val result: User? = response.body()
                    loginResult.postValue(true)
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
                    registrationResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<UserRegister>, t: Throwable) {
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
}