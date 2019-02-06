package com.greggz77.bcodingchallenge

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.albums_list.view.*

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        usersRecView.layoutManager = LinearLayoutManager(this)
        usersRecView.adapter = AlbumsAdapter()
    }

    private class AlbumsAdapter: RecyclerView.Adapter<AlbumsViewHolder>() {

        val albums = listOf("čsdlkmčsngfn", "sldnfslglfgndffdč", "qšppefsdbvgfmdcv", "eegeogundfmngdfjpi")

        //counts elements in array/list
        override fun getItemCount(): Int {
            return albums.size
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AlbumsViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val cellForRow = layoutInflater.inflate(R.layout.albums_list, p0, false)
            return AlbumsViewHolder(cellForRow)
        }

        override fun onBindViewHolder(p0: AlbumsViewHolder, p1: Int) {
            val album = albums.get(p1)
            p0.itemView.albumText?.text = album

        }
    }

    private class AlbumsViewHolder(val albView: View): RecyclerView.ViewHolder(albView) {

        init {
            albView.setOnClickListener {

                val intent = Intent(albView.context, GridActivity::class.java)
                albView.context.startActivity(intent)
            }
        }

    }
}