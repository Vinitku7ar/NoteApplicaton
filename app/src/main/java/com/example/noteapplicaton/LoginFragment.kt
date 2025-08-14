package com.example.noteapplicaton

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapplicaton.databinding.FragmentLoginBinding
import com.example.noteapplicaton.models.UserRequest
import com.example.noteapplicaton.utils.NetworkResult
import com.example.noteapplicaton.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding?=null
    private val binding get()=_binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root

       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            val validationResult =validateUserInput()
            if(validationResult.first){
                authViewModel.loginUser(getUserRequest())
            }
            else{
                binding.txtError.text=validationResult.second
            }

        }
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()

        }
        bindObserver()
    }

    private fun bindObserver() {
          authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
              binding.progressBar.isVisible=false
              when (it){
                  is NetworkResult.Sucess-> {
                      //token
                      tokenManager.saveToken(it.data!!.token)
                      findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                  }
                  is NetworkResult.Error -> {
                      binding.txtError.text=it.message
                  }
                  is NetworkResult.Loading ->{
                      binding.progressBar.isVisible=true
                  }

              }
          })
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest=getUserRequest()
        return authViewModel.validateCredential(userRequest.username,userRequest.email,userRequest.password,true)
    }

    private fun getUserRequest():UserRequest {

            val emailAddress = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            return UserRequest(emailAddress,password,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }


}