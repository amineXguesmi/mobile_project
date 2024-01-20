package com.example.mobile_project.ui.fragments.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.databinding.FragmentBasicInformationBinding
import com.example.mobile_project.databinding.FragmentEmailBinding
import com.example.mobile_project.ui.screens.HomePage
import com.example.mobile_project.ui.screens.LoginScreen

class BasicInformationFragment : Fragment() {

    private var _binding: FragmentBasicInformationBinding? = null
    private val binding get() = _binding!!
    private lateinit var backLinearLayout: LinearLayout
    private lateinit var signUpButton: View
    private val viewModel: UserVm by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicInformationBinding.inflate(inflater, container, false)
        backLinearLayout = binding.back
        signUpButton = binding.signup
        backLinearLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_basicInformationFragment_to_passwordFragment)
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.registrationResult.observe(this) { registrationSuccessful ->
            if (registrationSuccessful) {
                val intent = Intent(activity, HomePage::class.java)
                startActivity(intent)
            }
        }
        viewModel.signupErrorLiveData.observe(this) { errorMessage ->
            if (errorMessage != null) {
                view.findNavController().navigate(R.id.action_basicInformationFragment_to_emailFragment)
            }
        }
        signUpButton.setOnClickListener {
            viewModel.registerUser()
        }
    }
}