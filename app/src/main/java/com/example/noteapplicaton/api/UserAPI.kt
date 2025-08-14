package com.example.noteapplicaton.api

import com.example.noteapplicaton.models.UserRequest
import com.example.noteapplicaton.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    //this signup method is post method so we declare as POST
    // and whenever we do post method we have pass @body annotation too
    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>

    //same with signin method also
    @POST("/users/signin")
    suspend fun signin(@Body userRequest: UserRequest): Response<UserResponse>


//NOW RETROFIT INTERFACE BODY IS READY TO USE AND READY , THIS CLASS INTERACT WITH CHECKPOINT URL
    //AND CREATE USER INFO

}