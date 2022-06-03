package com.example.magazin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.magazin.api.ApiInterface
import com.example.magazin.data.UsersDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://10.0.2.2:8080/"
var flag = false

var globalUsername = ""
var globalMagazin = ""
var globalProdus = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = false;

        configureLogIn()

        configureSignIn()
    }

    private fun configureSignIn() {
        val button = findViewById<Button>(R.id.signIn)
        val username = findViewById<TextView>(R.id.editTextTextPersonName)
        val password = findViewById<TextView>(R.id.editTextTextPassword)

        button.setOnClickListener {

            //println("Here")

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)

            val userToAdd = UsersDataItem(username.text.toString(),password.text.toString())

            val retrofitData = retrofitBuilder.addUser(userToAdd)

            println(userToAdd.username + " " + userToAdd.password)

            retrofitData.enqueue(object : Callback<UsersDataItem?> {
                override fun onResponse(
                    call: Call<UsersDataItem?>,
                    response: Response<UsersDataItem?>
                ) {
                    finish()
                }

                override fun onFailure(call: Call<UsersDataItem?>, t: Throwable) {
                    Log.d("Main","Failure" + t.message)
                }
            })
        }
    }

    private fun configureLogIn() {
        val button = findViewById<Button>(R.id.logIn)
        val username = findViewById<TextView>(R.id.editTextTextPersonName)
        val password = findViewById<TextView>(R.id.editTextTextPassword)

        button.setOnClickListener {

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)

            val retrofitData = retrofitBuilder.getUsers()

            retrofitData.enqueue(object : Callback<List<UsersDataItem>?> {
                override fun onResponse(
                    call: Call<List<UsersDataItem>?>,
                    response: Response<List<UsersDataItem>?>
                ) {
                    val responseBody = response.body()!!


                    val username = findViewById<TextView>(R.id.editTextTextPersonName)
                    val password = findViewById<TextView>(R.id.editTextTextPassword)

                    for(data in responseBody){
                        if(username.text.toString().equals(data.username) and password.text.toString().equals(data.password)){
                            flag = true;
                            globalUsername = data.username
                        }



                    }

                }

                override fun onFailure(call: Call<List<UsersDataItem>?>, t: Throwable) {
                    Log.d("Main","Failure" + t.message)
                }
            })

                    println(flag)

                    if(flag){
                        val intent = Intent(this,ListaMagazine::class.java)
                        startActivity(intent)
                    }


        }
    }


}
















