package com.example.magazin.api

import com.example.magazin.data.ProduseDataItem
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterfaceProduse {

    @GET("produse/{numeMagazin}")
    fun getProduse(@Path("numeMagazin") numeMagazin : String): Call<List<ProduseDataItem>>
}