package com.example.noteapplicaton

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapplicaton.databinding.FragmentRegisterBinding
import com.example.noteapplicaton.models.User
import com.example.noteapplicaton.models.UserRequest
import com.example.noteapplicaton.utils.NetworkResult
import com.example.noteapplicaton.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding :FragmentRegisterBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     _binding =  FragmentRegisterBinding.inflate(inflater,container,false)
        //navigate to login page
        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
         //return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSignUp.setOnClickListener{
            val validationResult =validateUserInput()
            if(validationResult.first){
                authViewModel.resgisterUser(getUserRequest())

            }
            else{
                binding.txtError.text=validationResult.second
            }

        }

        binding.btnLogin.setOnClickListener{
             findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        bindObservers()


    }
    private fun getUserRequest():UserRequest{
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val username =binding.txtUsername.text.toString()
        return UserRequest(emailAddress,password,username)
    }
    private fun validateUserInput(): Pair<Boolean, String> {
       val userRequest=getUserRequest()

        return authViewModel.validateCredential(userRequest.username,userRequest.email,userRequest.password,false)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Sucess -> {
                    //token
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading->{
                    binding.progressBar.isVisible=true
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}

/*steps to enable viewBinding
1-> initialse bindig object
       initialize with can be null property ?=null
       ab koi bhi view ko pass karne se pehle non null asserted use karna padega
       is liye hum dubblicate binding bana kar nnull asserted use kar leneg !!
2->inflate karo

3-> use kRO LIKE THIS
binding.idKaNaam

4-> ab performance inhance ke liye bindig object ko destroy karte hh kyuki activity
destroy hone ke baad binding object ka kaam kyu

*/