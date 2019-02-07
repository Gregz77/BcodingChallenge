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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.albums_list.view.*
import okhttp3.*
import java.io.IOException

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //display back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        usersRecView.layoutManager = LinearLayoutManager(this)
        //usersRecView.adapter = AlbumsAdapter()

        //change toolbar title
        val toolbarTitle = intent.getStringExtra(CustomViewHolder.USERNAME_KEY)
        supportActionBar?.title = toolbarTitle


        getAlbums()
    }

    private fun getAlbums() {

        val albumsUrl = "https://jsonplaceholder.typicode.com/albums"

        val request = Request.Builder().url(albumsUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()
                val albumFeed = gson.fromJson(body, Array<Album>::class.java)
                runOnUiThread {
                    usersRecView.adapter = AlbumsAdapter(albumFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

        })



    }
    

    private class AlbumsAdapter(val albumFeed: Array<Album>): RecyclerView.Adapter<AlbumsViewHolder>() {

        //val albums = listOf("čsdlkmčsngfn", "sldnfslglfgndffdč", "qšppefsdbvgfmdcv", "eegeogundfmngdfjpi")

        //counts elements in array/list
        override fun getItemCount(): Int {
            return albumFeed.count()
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AlbumsViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val cellForRow = layoutInflater.inflate(R.layout.albums_list, p0, false)
            return AlbumsViewHolder(cellForRow, album = null)
        }

        override fun onBindViewHolder(p0: AlbumsViewHolder, p1: Int) {
            val albums = albumFeed.get(p1)
            p0.itemView.albumText?.text = albums.title

            p0.album = albums

        }
    }

    private class AlbumsViewHolder(val albView: View, var album: Album?): RecyclerView.ViewHolder(albView) {

        companion object {
            val ALBUM_TITLE_KEY = "TITLE"
            val ALBUM_ID_KEY = "ALBUM_ID"
        }

        init {
            albView.setOnClickListener {

                val intent = Intent(albView.context, GridActivity::class.java)

                intent.putExtra(ALBUM_TITLE_KEY, album?.title)
                intent.putExtra(ALBUM_ID_KEY, album?.id)


                albView.context.startActivity(intent)
            }
        }

    }
}