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
        const val POST_LOAD_LIMIT = 10
    }

    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        postAdapter = PostAdapter()

        content_list.apply {
            adapter = postAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation))
            addOnScrollListener(InfiniteScrollListener(linearLayoutManager) {
                Log.e(TAG, "Bottom reached. Let's load items!")
                this.post { makeRequest(postAdapter.getPostCount()) }
            })
        }

        // Initial request
        makeRequest(0)

        test_button.setOnClickListener { makeRequest(postAdapter.getPostCount()) }
    }

    private fun makeRequest(start: Int) {
        postApi()
            .getPosts(start, POST_LOAD_LIMIT)
            .enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.e(TAG, "Loading posts failed.")
                }

                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.isNotEmpty()) {
                                postAdapter.loadPosts(it)
                                Log.d(TAG, "posts loaded")
                            } else {
                                postAdapter.hideLoadingBar()
                                Log.d(TAG, "hide loading bar")
                            }
                        } ?: run {
                            Log.e(TAG, "posts are not loaded")
                        }
                    } else {
                        Log.e(TAG, "response is not successful")
                    }
                }
            })
    }
}
