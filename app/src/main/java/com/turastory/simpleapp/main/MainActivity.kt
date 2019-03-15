package com.turastory.simpleapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.turastory.simpleapp.R
import com.turastory.simpleapp.network.doOnSuccess
import com.turastory.simpleapp.network.postApi
import kotlinx.android.synthetic.main.activity_main.*

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
                this.post { makeRequest(postAdapter.getPostCount()) }
            })
        }

        // Initial request
        makeRequest(0)
    }

    private fun makeRequest(start: Int) {
        postApi()
            .getPosts(start, POST_LOAD_LIMIT)
            .doOnSuccess {
                if (it.isNotEmpty()) {
                    postAdapter.loadPosts(it)

                    // For initial load, scroll up to prevent weirdly going down.
                    if (start == 0)
                        content_list.scrollToPosition(0)
                } else {
                    postAdapter.hideLoadingBar()
                }
            }
            .doOnFailed {
                Log.e(TAG, "Error while loading posts - $it")
            }
            .done()
    }
}
