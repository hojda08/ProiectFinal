package com.example.magazin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.magazin.api.ApiInterfaceMagazine
import com.example.magazin.api.ApiInterfaceProduse
import com.example.magazin.data.ProduseDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var produse = arrayOf("")
var flagProduseUpdate = true
var numeProdusNext = ""

class Produse : AppCompatActivity() {

    var flagArr = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produse)


        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterfaceProduse::class.java)

        val retrofitData = retrofitBuilder.getProduse(numeMagazinNext)

        retrofitData.enqueue(object : Callback<List<ProduseDataItem>?> {
            override fun onResponse(
                call: Call<List<ProduseDataItem>?>,
                response: Response<List<ProduseDataItem>?>
            ) {
                val responseBody = response.body()!!

                if(flagProduseUpdate){

                    produse = arrayOf("")

                    for(data in responseBody){

                        if(flagArr){
                            produse = produse.plusElement(data.numeProdus + "     pret: " + data.pret + "RON"
                                    + "\ncantitate: " + data.cantitate)
                        }

                        //magazine = magazine.plusElement(data.nume)

                        println(data.numeProdus + "  " + data.pret)
                    }

                    flagArr = false

                    flagProduseUpdate = false
                }
            }

            override fun onFailure(call: Call<List<ProduseDataItem>?>, t: Throwable) {
                Log.d("ProduseFrame","Failure" + t.message)
            }
        })


        val listview =findViewById<ListView>(R.id.listProduse)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,produse)
        listview.adapter = adapter
        listview.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                var produseNext = findViewById<TextView>(R.id.produsTextNext)

                numeProdusNext = produseNext.text.toString()

                println(numeProdusNext)

                goDescriere()
            }
        }

        configureReload()

        goBack()

    }

    private fun goDescriere(){
        flagProduseUpdate = true
        val intent = Intent(this,Descriere::class.java)
        startActivity(intent)
    }

    private fun configureReload() {
        val button = findViewById<Button>(R.id.reloadProduseButton)

        button.setOnClickListener {
            val intent = Intent(this,Produse::class.java)
            startActivity(intent)
        }
    }

    private fun goBack(){
        val button = findViewById<Button>(R.id.buttonBackMagazine);

        flagProduseUpdate = true

        button.setOnClickListener {
            val intent = Intent(this,ListaMagazine::class.java)
            startActivity(intent)
        }
    }
}