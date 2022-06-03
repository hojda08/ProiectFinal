package com.example.magazin.api

import com.example.magazin.data.ReviewsDataItem
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterfaceReviews {
    @GET("reviews/{numeProdus}")
    fun getReviews(@Path("numeProdus") numeProdus : String): Call<List<ReviewsDataItem>>
}