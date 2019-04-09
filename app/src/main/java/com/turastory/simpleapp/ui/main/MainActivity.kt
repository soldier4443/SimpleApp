package com.turastory.simpleapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.adapter.PostAdapter
import com.turastory.simpleapp.util.InfiniteScrollListener
import com.turastory.simpleapp.util.RecyclerViewItemClickListener
import com.turastory.simpleapp.ext.toast
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
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    linearLayoutManager.orientation
                )
            )

            addOnScrollListener(InfiniteScrollListener(linearLayoutManager) {
                this.post { presenter.requestNewPosts() }
            })

            addOnItemTouchListener(
                RecyclerViewItemClickListener(
                    this@MainActivity,
                    this,
                    { _, pos ->
                        if (postAdapter.isNotLoading(pos)) {
                            presenter.onItemClick(pos)
                        }
                    })
            )
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

        postAdapter.addPosts(posts)

        // For initial load, scroll up to prevent weirdly going down.
        if (beforeCount == 0)
            content_list.scrollToPosition(0)
    }

    override fun hideDeletedPost(deletedPostId: Int) {
        postAdapter.removePost(deletedPostId)
    }

    override fun openDetailsView(id: Int) {
        startActivityForResult(
            Intent(this, DetailsActivity::class.java)
                .putExtra("postId", id),
            MainContract.REQUEST_DETAILS
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainContract.REQUEST_DETAILS) {
            handleDetailsResult(resultCode, data)
        }
    }

    private fun handleDetailsResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.getIntExtra("deletedPostId", -1)?.let {
                    if (it != -1) {
                        toast("Deleting post complete!")
                        presenter.notifyPostDeleted(it)
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                // Do nothing
            }
            else -> throw IllegalStateException("Illegal result code")
        }
    }
}
