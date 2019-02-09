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
        val albumTitle = intent.getStringExtra(AlbumsActivity.AlbumsViewHolder.ALBUM_TITLE_KEY)
        val imageTitle = intent.getStringExtra(GridActivity.GridViewHolder.IMAGE_TITLE_KEY)
        val imageURL = intent.getStringExtra(GridActivity.GridViewHolder.ALBUM_PHOTO_URL_KEY)
        val userName = intent.getStringExtra(GridActivity.GridViewHolder.USER_NAME_KEY)


        runOnUiThread {
            RecView.adapter = PhotoActivity.PhotoAdapter(imageURL = imageURL, albumTitle = albumTitle, imageTitle = imageTitle, userName = userName, toolbar = toolbar)
        }
    }

    private class PhotoAdapter (val imageURL: String, val albumTitle: String, val imageTitle: String, val userName: String, var toolbar: Toolbar): RecyclerView.Adapter<PhotoViewHolder>() {

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val customView = layoutInflater.inflate(R.layout.photo_layout, p0, false)
            return PhotoViewHolder(customView, toolbar = toolbar)
        }

        override fun onBindViewHolder(p0: PhotoViewHolder, p1: Int) {
            Picasso.get().load(imageURL).into(p0.itemView.fullScreenView)
            p0.itemView.albumTitleText?.text = albumTitle
            p0.itemView.imageTitleText?.text = imageTitle
            p0.itemView.userNameText?.text = userName
        }
    }

    class PhotoViewHolder(itemView: View, val toolbar: Toolbar) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener {
                if (itemView.imageTitleText.visibility == View.VISIBLE){
                    itemView.imageTitleText.visibility = View.INVISIBLE
                    itemView.albumTitleText.visibility = View.INVISIBLE
                    itemView.userNameText.visibility = View.INVISIBLE
                    toolbar.visibility = View.INVISIBLE
                } else {
                    itemView.imageTitleText.visibility = View.VISIBLE
                    itemView.albumTitleText.visibility = View.VISIBLE
                    itemView.userNameText.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
            }
        }
    }
}