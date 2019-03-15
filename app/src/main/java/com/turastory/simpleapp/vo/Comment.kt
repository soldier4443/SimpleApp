package com.turastory.simpleapp.vo

data class Comment(val id: Int,
                   val postId: Int,
                   val name: String,
                   val body: String)