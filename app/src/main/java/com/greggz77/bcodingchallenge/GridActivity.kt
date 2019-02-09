package com.greggz77.bcodingchallenge

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_layout.view.*
import okhttp3.*
import java.io.IOException


class GridActivity : AppCompatActivity() {

    var userName = ""
    var albumTitle = ""

    var photosToShow: MutableList<Photo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        RecView.layoutManager = GridLayoutManager(this, 2)

        //change toolbar title
        albumTitle = intent.getStringExtra(AlbumsActivity.AlbumsViewHolder.ALBUM_TITLE_KEY)
        val toolbarTitle = albumTitle
        supportActionBar?.title = toolbarTitle
        //add scroll effect!!

        userName = intent.getStringExtra(AlbumsActivity.AlbumsViewHolder.USER_NAME_KEY)
        getPhotos()
    }

    private fun getPhotos() {

        val photosUrl = "https://jsonplaceholder.typicode.com/photos"

        val request = Request.Builder().url(photosUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                //gets all photo data
                val gson = GsonBuilder().create()
                val photosFeed = gson.fromJson(body, Array<Photo>::class.java)

                val albumId = intent.getIntExtra(AlbumsActivity.AlbumsViewHolder.ALBUM_ID_KEY, -1)
                //photos for selected album
                //val photosForAlbum: MutableList<Photo> = arrayListOf()//arrayListOf<Photo>()

                if (photosFeed != null && albumId > 0) {
                    for (photo in photosFeed) {
                        if (albumId == photo.albumId) {
                            if (photo.thumbnailUrl != null) {
                                photosToShow.add(photo)
                            }
                        }
                    }
                }
                runOnUiThread {
                    RecView.adapter = GridActivity.GridAdapter(photosToShow, userName = userName, albumTitle = albumTitle)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

    private class GridAdapter (val photosToShow: MutableList<Photo>, val userName: String, val albumTitle: String): RecyclerView.Adapter<GridViewHolder>() {

        override fun getItemCount(): Int {
            return photosToShow.count()
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GridViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val customView = layoutInflater.inflate(R.layout.grid_layout, p0, false)
            return GridActivity.GridViewHolder(customView, photo = null, userName = userName, albumTitle = albumTitle)
        }

        override fun onBindViewHolder(p0: GridViewHolder, p1: Int) {
            val photo = photosToShow[p1]
            p0.itemView.photoText1?.text = photo.title
            Picasso.get().load(photo.thumbnailUrl).into(p0.itemView.imageView1)
            p0.photo = photo
        }
    }

    class GridViewHolder(itemView: View, var photo: Photo?, val userName: String, val albumTitle: String) : RecyclerView.ViewHolder(itemView) {
        companion object {
            val ALBUM_TITLE_KEY = "TITLE"
            val IMAGE_TITLE_KEY = "IMAGE_TITLE"
            val ALBUM_PHOTO_URL_KEY = "ALBUM_PHOTO_URL"
            val USER_NAME_KEY = "USER_NAME"
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PhotoActivity::class.java)

                intent.putExtra(GridActivity.GridViewHolder.ALBUM_TITLE_KEY, albumTitle)
                intent.putExtra(GridActivity.GridViewHolder.USER_NAME_KEY, userName)
                intent.putExtra(GridActivity.GridViewHolder.IMAGE_TITLE_KEY, photo?.title)
                intent.putExtra (GridActivity.GridViewHolder.ALBUM_PHOTO_URL_KEY, photo?.url)

                itemView.context.startActivity(intent)
            }
        }
    }
}

