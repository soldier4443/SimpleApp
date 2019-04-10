package com.turastory.simpleapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.base.BaseActivity
import com.turastory.simpleapp.ext.getViewModel
import com.turastory.simpleapp.ext.injector
import com.turastory.simpleapp.ext.toast
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.adapter.NewPostAdapter
import com.turastory.simpleapp.util.InfiniteScrollListener
import com.turastory.simpleapp.util.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        private const val REQUEST_DETAILS = 100
    }

    // Get ViewModel
    @Inject
    lateinit var vmf: ViewModelProvider.Factory
    private val vm: MainViewModel by lazy { getViewModel<MainViewModel>(vmf) }

    private val postAdapter = NewPostAdapter()

    override fun inject() {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        vm.posts.observe(this, Observer {
            postAdapter.submitList(it)
        })

        vm.state.observe(this, Observer {
            postAdapter.networkStateChanged(it)
        })

        vm.navigateToDetails.observe(this, Observer {
            it.getContentIfNotHandled()?.let { id ->
                val intent = Intent(this, DetailsActivity::class.java)
                    .putExtra("postId", id)

                startActivityForResult(
                    intent,
                    REQUEST_DETAILS
                )
            }
        })
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
                this.post {
                    vm.loadPosts()
                }
            })

            addOnItemTouchListener(
                RecyclerViewItemClickListener(
                    this@MainActivity,
                    this,
                    { _, pos ->
                        vm.clickPostItemOn(pos)
                    })
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DETAILS) {
            handleDetailsResult(resultCode, data)
        }
    }

    private fun handleDetailsResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.getIntExtra("deletedPostId", -1)?.let { id ->
                    if (id != -1) {
                        toast("Deleting post complete!")
                        vm.deletePost(id)
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