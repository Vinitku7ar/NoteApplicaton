package com.example.noteapplicaton.respository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapplicaton.api.UserAPI
import com.example.noteapplicaton.models.UserRequest
import com.example.noteapplicaton.models.UserResponse
import com.example.noteapplicaton.utils.Constants.TAG
import com.example.noteapplicaton.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response

import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData =MutableLiveData<NetworkResult<UserResponse>>()

    val UserResponseLiveData :LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData //loding state


    suspend fun registerUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())//loading state
        val response = userAPI.signup(userRequest)
        handleResponse(response)
    }
    suspend fun loginUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())//loading state
        val response = userAPI.signin(userRequest)
        handleResponse(response)

    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Sucess(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


}