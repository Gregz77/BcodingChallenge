package com.greggz77.bcodingchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.photo_layout.view.*


class PhotoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        RecView.layoutManager = LinearLayoutManager(this)
        //change toolbar title
        val toolbarTitle = intent.getStringExtra(GridActivity.GridViewHolder.IMAGE_TITLE_KEY)
        supportActionBar?.title = toolbarTitle
        val albumTitle = intent.getStringExtra(GridActivity.GridViewHolder.ALBUM_TITLE_KEY)
        val imageTitle = intent.getStringExtra(GridActivity.GridViewHolder.IMAGE_TITLE_KEY)
        val imageURL = intent.getStringExtra(GridActivity.GridViewHolder.ALBUM_PHOTO_URL_KEY)
        val userName = intent.getStringExtra(GridActivity.GridViewHolder.USER_NAME_KEY)

        runOnUiThread {
            RecView.adapter = PhotoActivity.PhotoAdapter(imageURL = imageURL)
        }
    }

    private class PhotoAdapter (val imageURL: String): RecyclerView.Adapter<PhotoActivity.PhotoViewHolder>() {

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoActivity.PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val customView = layoutInflater.inflate(R.layout.photo_layout, p0, false)
            return PhotoActivity.PhotoViewHolder(customView)
        }

        override fun onBindViewHolder(p0: PhotoActivity.PhotoViewHolder, p1: Int) {
            Picasso.get().load(imageURL).into(p0.itemView.fullScreenView)
            //add other data here
        }
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
            }

        }

    }
}