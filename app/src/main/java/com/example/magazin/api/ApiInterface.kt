package com.example.magazin.api


import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("users")
    fun getUsers(): Call<List<UsersDataItem>>

    @POST("users")
    fun addUser(@Body user : UsersDataItem) : Call<UsersDataItem>


}