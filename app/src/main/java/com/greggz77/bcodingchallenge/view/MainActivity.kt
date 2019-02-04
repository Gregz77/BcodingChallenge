package com.greggz77.bcodingchallenge.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.greggz77.bcodingchallenge.R
import com.greggz77.bcodingchallenge.contract.ContractInterface.View
import com.greggz77.bcodingchallenge.presenter.MainActivityPresenter


class MainActivity : AppCompatActivity(), View {

    private var presenter: MainActivityPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implements action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //displays in-app position

        //displays back button
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        presenter = MainActivityPresenter(this)

    }
}
