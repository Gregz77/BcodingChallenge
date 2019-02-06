package com.greggz77.bcodingchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implement action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            val users = ArrayList<String>()
            users.add("{name\": \"Leanne Graham\",\n" +
                    "    \"username\": \"Bret\",}")
            users.add("{name\": \"Ervin Howell\",\n" +
                    "    \"username\": \"Antonette\"}")


        usersRecView.layoutManager = LinearLayoutManager(this)
        usersRecView.adapter = MainAdapter()

        //getUsers()
        }

    /*fun getUsers(){

        val url = "https://jsonplaceholder.typicode.com/users"

    }*/

    }
