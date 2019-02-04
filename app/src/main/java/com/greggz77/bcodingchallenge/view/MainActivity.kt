package com.greggz77.bcodingchallenge.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.greggz77.bcodingchallenge.R
import com.greggz77.bcodingchallenge.contract.ContractInterface
import com.greggz77.bcodingchallenge.presenter.MainActivityPresenter


class MainActivity : AppCompatActivity(), ContractInterface.View {

    private var presenter: MainActivityPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)

    }
}
