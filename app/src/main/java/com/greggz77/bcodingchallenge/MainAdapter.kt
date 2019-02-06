package com.greggz77.bcodingchallenge

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.users_list.view.*

class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {


    val users = listOf("dfjlgfdl", "dflgdlfd", "oldrgdlogfd", "lsfdgnlsfdgnlk")

    //counts elements in array/list
    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.users_list, p0, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val userName = users.get(p1)
        p0.itemView.usernameText?.text = userName
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {

            val intent = Intent(view.context, AlbumsActivity::class.java)
            view.context.startActivity(intent)
        }
    }

}