package com.example.magazin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.magazin.api.ApiInterface
import com.example.magazin.api.ApiInterfaceMagazine
import com.example.magazin.data.MagazineDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var magazine = arrayOf("")

var flagMagazineUpdate = true

var numeMagazinNext = ""

class ListaMagazine : AppCompatActivity() {


    var flagArr = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lista_magazine)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterfaceMagazine::class.java)

        val retrofitData = retrofitBuilder.getMagazine()

        retrofitData.enqueue(object : Callback<List<MagazineDataItem>?> {
            override fun onResponse(
                call: Call<List<MagazineDataItem>?>,
                response: Response<List<MagazineDataItem>?>
            ) {

                val responseBody = response.body()!!

                if(flagMagazineUpdate){

                    for(data in responseBody){

                        if(flagArr){
                            magazine = magazine.plusElement(data.nume + "     rating: " + data.rating)
                        }

                        //magazine = magazine.plusElement(data.nume)

                        println(data.nume + "  " + data.rating)
                    }

                    flagArr = false

                    flagMagazineUpdate = false
                }



            }

            override fun onFailure(call: Call<List<MagazineDataItem>?>, t: Throwable) {
                Log.d("MagazineFrame","Failure" + t.message)
            }
        })


        setContentView(R.layout.activity_lista_magazine)

        val listview =findViewById<ListView>(R.id.list)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,magazine)
        listview.adapter = adapter
        listview.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val magazineNext = findViewById<TextView>(R.id.magazinText)

                numeMagazinNext = magazineNext.text.toString()

                println(numeMagazinNext)

                goProduse()
            }
        }


        configureBack()
        configureReload()
    }

    private fun configureReload() {
        val button = findViewById<Button>(R.id.reloadButton)

        button.setOnClickListener {
            val intent = Intent(this,ListaMagazine::class.java)
            startActivity(intent)
        }
    }

    private fun goProduse() {
        val intent = Intent(this,Produse::class.java)
        startActivity(intent)
    }

    private fun configureBack() {
        val button = findViewById<Button>(R.id.buttonBack);

        button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}