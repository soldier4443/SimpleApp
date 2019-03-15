package com.turastory.simpleapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.turastory.simpleapp.R
import com.turastory.simpleapp.network.postApi
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content_list.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)

            adapter = postAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation))
        }

        test_button.setOnClickListener { makeRequest() }
    }

    private fun makeRequest() {
        postApi()
            .getPosts(0, 10)
            .enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.e(TAG, t.message)
                }

                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            postAdapter.loadPosts(it)
                            Log.d(TAG, "posts loaded")
                        } ?: run {
                            Log.e(TAG, "posts not loaded")
                        }
                    } else {
                        Log.e(TAG, "What happens?")
                    }
                }
            })
    }
}
