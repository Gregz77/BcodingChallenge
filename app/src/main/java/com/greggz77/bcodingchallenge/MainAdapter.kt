package com.greggz77.bcodingchallenge

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.users_list.view.*

class MainAdapter(val userFeed: Array<User>): RecyclerView.Adapter<CustomViewHolder>() {

    //counts elements in array/list
    override fun getItemCount(): Int {
        return   userFeed.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.users_list, p0, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val user = userFeed.get(p1)
        p0.view.usernameText?.text = user.username
        p0.view.nameText?.text = user.name

        p0.user = user
    }
}

class CustomViewHolder(val view: View, var user: User? = null): RecyclerView.ViewHolder(view) {

    companion object {
        val USERNAME_KEY = "USERNAME"
        val USER_ID_KEY = "USER_ID"
        val USER_NAME_KEY = "USER_NAME"
    }

    init {
        view.setOnClickListener {

            val intent = Intent(view.context, AlbumsActivity::class.java)

            intent.putExtra(USERNAME_KEY, user?.username)
            intent.putExtra(USER_ID_KEY, user?.id)
            intent.putExtra(USER_NAME_KEY, user?.name)

            view.context.startActivity(intent)
        }
    }
}