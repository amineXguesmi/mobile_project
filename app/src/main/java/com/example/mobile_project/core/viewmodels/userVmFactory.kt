package com.example.mobile_project.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_project.core.services.UserService


class UserVmFactory (private val userService: UserService = UserService()) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserVm::class.java)) {
            return UserVm(userService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}