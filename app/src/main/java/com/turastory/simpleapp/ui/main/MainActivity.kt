package com.turastory.simpleapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.adapter.PostAdapter
import com.turastory.simpleapp.util.InfiniteScrollListener
import com.turastory.simpleapp.util.RecyclerViewItemClickListener
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter: MainContract.Presenter by inject()
    private val postAdapter: PostAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()

        // Initial request
        presenter.setView(this@MainActivity)
        presenter.requestNewPosts()
    }

    private fun setupToolbar() {
        setSupportActionBar(main_toolbar)
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)

        content_list.apply {
            adapter = postAdapter
            layoutManager = linearLayoutManager

            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation))

            addOnScrollListener(InfiniteScrollListener(linearLayoutManager) {
                this.post { presenter.requestNewPosts() }
            })

            addOnItemTouchListener(RecyclerViewItemClickListener(this@MainActivity, this, { _, pos ->
                if (postAdapter.isNotLoading(pos)) {
                    presenter.onItemClick(pos)
                }
            }))
        }
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

    override fun openDetailsView(id: Int) {
        startActivity(Intent(this, DetailsActivity::class.java)
            .putExtra("postId", id))
    }
}
