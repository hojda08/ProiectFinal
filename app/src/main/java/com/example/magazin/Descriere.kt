package com.example.magazin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.magazin.api.ApiInterface
import com.example.magazin.api.ApiInterfaceComenzi
import com.example.magazin.api.ApiInterfaceReviews
import com.example.magazin.data.ComenziDataItem
import com.example.magazin.data.ReviewsDataItem
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Descriere : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriere)






        configureCumpara()

        configureReload()
    }

    private fun configureReload() {
        val button = findViewById<Button>(R.id.buttonReloadReview)
        var numeProdus = findViewById<TextView>(R.id.numeProdText)
        var descriere = findViewById<TextView>(R.id.descriereText)

        button.setOnClickListener {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterfaceReviews::class.java)

            val retrofitData = retrofitBuilder.getReviews(numeProdusNext)

            retrofitData.enqueue(object : Callback<List<ReviewsDataItem>?> {
                override fun onResponse(
                    call: Call<List<ReviewsDataItem>?>,
                    response: Response<List<ReviewsDataItem>?>
                ) {
                    val responseBody = response.body()!!

                    var stringBuilder = StringBuilder()

                    for(data in responseBody){
                        stringBuilder.append(data.text + "\n")
                    }

                    numeProdus.text = numeProdusNext

                    descriere.text = stringBuilder.toString()

                }

                override fun onFailure(call: Call<List<ReviewsDataItem>?>, t: Throwable) {
                    Log.d("Descriere","Failure" + t.message)
                }
            })
        }
    }

    private fun configureCumpara() {
        val button = findViewById<Button>(R.id.buttonCumparaProdus)
        var cantitateTxt = findViewById<TextView>(R.id.cantitateText)

        button.setOnClickListener {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterfaceComenzi::class.java)

            val comandaToAdd = ComenziDataItem(cantitateTxt.text.toString().toInt(),13,
                numeProdusNext, globalUsername)

            val retrofitData = retrofitBuilder.addComanda(comandaToAdd)

            retrofitData.enqueue(object : Callback<ComenziDataItem?> {
                override fun onResponse(
                    call: Call<ComenziDataItem?>,
                    response: Response<ComenziDataItem?>
                ) {
                    finish()
                }

                override fun onFailure(call: Call<ComenziDataItem?>, t: Throwable) {
                    Log.d("Descriere","Failure" + t.message)
                }
            })

            println(comandaToAdd.numeProdus + " " + comandaToAdd.cantitate)
        }
    }
}