package com.example.magazin.api

import com.example.magazin.data.MagazineDataItem
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterfaceMagazine {

    @GET("magazine")
    fun getMagazine(): Call<List<MagazineDataItem>>
}