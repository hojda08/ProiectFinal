package com.example.magazin.api

import com.example.magazin.data.ComenziDataItem
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterfaceComenzi {
    @POST("comenzi")
    fun addComanda(@Body comanda : ComenziDataItem) : Call<ComenziDataItem>
}