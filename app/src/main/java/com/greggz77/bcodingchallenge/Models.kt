package com.greggz77.bcodingchallenge

class User(val id: Int, val username: String, val name: String)

class Album(val userId: Int, val id: Int, val title: String)


class Photo(val albumId: Int, val id: Int, val title: String, val url: String, val thumbnailUrl: String)
