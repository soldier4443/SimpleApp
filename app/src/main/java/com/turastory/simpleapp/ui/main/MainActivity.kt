package com.turastory.simpleapp.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.ui.main.adapter.InfiniteScrollListener
import com.turastory.simpleapp.ui.main.adapter.PostAdapter
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private val postAdapter: PostAdapter = PostAdapter()
    private val presenter: MainContract.Presenter by lazy {
        MainPresenter().apply {
            setView(this@MainActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this@MainActivity)

        content_list.apply {
            adapter = postAdapter
            layoutManager = linearLayoutManager

            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation))

            addOnScrollListener(InfiniteScrollListener(linearLayoutManager) {
                this.post { presenter.requestNewPosts() }
            })
        }

        // Initial request
        presenter.requestNewPosts()
    }

    override fun showLoadingBar() {
        postAdapter.showLoadingBar()
    }

    override fun hideLoadingBar() {
        postAdapter.hideLoadingBar()
    }

    override fun showNewPosts(posts: List<Post>) {
        val beforeCount = postAdapter.getPostCount()

        postAdapter.showNewPosts(posts)

        // For initial load, scroll up to prevent weirdly going down.
        if (beforeCount == 0)
            content_list.scrollToPosition(0)
    }
}
