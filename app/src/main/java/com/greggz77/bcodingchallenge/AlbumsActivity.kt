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
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.albums_list.view.*
import okhttp3.*
import java.io.IOException

class AlbumsActivity : AppCompatActivity() {

    var photosFeed: MutableList<Photo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        RecView.layoutManager = LinearLayoutManager(this)

        //change toolbar title
        val toolbarTitle = intent.getStringExtra(CustomViewHolder.USERNAME_KEY)
        supportActionBar?.title = toolbarTitle

        getPhotos()
    }

    private fun getAlbums() {
        val albumsUrl = "https://jsonplaceholder.typicode.com/albums"
        val idOfUser = intent.getIntExtra(CustomViewHolder.USER_ID_KEY, -1)

        val request = Request.Builder().url(albumsUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                val albumFeed = gson.fromJson(body, Array<Album>::class.java)

                val albumsForUser: MutableList<Album> = arrayListOf<Album>()
                for (album in albumFeed) {
                    if (album.userId == idOfUser){
                        albumsForUser.add(album)
                    }
                }

                runOnUiThread {
                    RecView.adapter = AlbumsAdapter(albumsForUser, photosFeed = photosFeed)//fix this
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

    private fun getPhotos() {

        val photosUrl = "https://jsonplaceholder.typicode.com/photos"

        val request = Request.Builder().url(photosUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                //println(body)

                //gets all photo data
                val gson = GsonBuilder().create()
                val photosFeedArray = gson.fromJson(body, Array<Photo>::class.java)
                photosFeed = photosFeedArray.toMutableList()
                getAlbums()
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

   private class AlbumsAdapter(val albumFeed: MutableList<Album>, val photosFeed: MutableList<Photo>?): RecyclerView.Adapter<AlbumsViewHolder>() {

        //counts elements in array/list
        override fun getItemCount(): Int {
            return albumFeed.size
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AlbumsViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val customView = layoutInflater.inflate(R.layout.albums_list, p0, false)
            return AlbumsViewHolder(customView, album = null, photosFeed = photosFeed) //TO DO fix album issue!!
        }

        override fun onBindViewHolder(p0: AlbumsViewHolder, p1: Int) {
            val album = albumFeed[p1]
            p0.itemView.albumText?.text = album.title

            p0.album = album

            //assigns a thumbnail
            if (photosFeed != null) {
                for (photo in photosFeed) {
                    if (album.id == photo.albumId) {
                        val thumbAlbumView = p0.itemView.albumThumb
                        Picasso.get().load(photo.thumbnailUrl).into(thumbAlbumView)
                        break
                    }
                }
            }
        }
    }

    class AlbumsViewHolder(val albView: View, var album: Album? = null, var photosFeed: MutableList<Photo>?): RecyclerView.ViewHolder(albView) {

        companion object {
            val ALBUM_TITLE_KEY = "TITLE"
            val ALBUM_ID_KEY = "ALBUM_ID"
            //val ALBUM_PHOTOS_KEY = "ALBUM_PHOTOS"
            val USER_NAME_KEY = "USER_NAME"
        }

        init {
            albView.setOnClickListener {
                val intent = Intent(albView.context, GridActivity::class.java)

                intent.putExtra(ALBUM_TITLE_KEY, album?.title)
                intent.putExtra(ALBUM_ID_KEY, album?.id)
                intent.putExtra(USER_NAME_KEY, intent.getStringExtra(CustomViewHolder.USER_NAME_KEY))

                albView.context.startActivity(intent)
            }
        }
    }
}