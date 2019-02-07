package com.greggz77.bcodingchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implement action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        usersRecView.layoutManager = LinearLayoutManager(this)
        //usersRecView.adapter = MainAdapter()

        //fetchJson()
        getUsers()
    }

    fun getUsers() {

        val url = "https://jsonplaceholder.typicode.com/users"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()
                val userFeed = gson.fromJson(body, Array<User>::class.java)
                runOnUiThread {
                    usersRecView.adapter = MainAdapter(userFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

        })



    }

}