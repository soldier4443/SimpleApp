package com.turastory.simpleapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.base.BaseActivity
import com.turastory.simpleapp.ext.injector
import com.turastory.simpleapp.ext.observe
import com.turastory.simpleapp.ext.toast
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.adapter.PostAdapter
import com.turastory.simpleapp.util.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        private const val REQUEST_DETAILS = 100
    }

    // Get ViewModel
    @Inject
    lateinit var vm: MainViewModel

    private val postAdapter = PostAdapter()

    override fun inject() {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupPostList()

        observe(vm.navigateToDetails) {
            it.getContentIfNotHandled()?.let { id ->
                val intent = Intent(this, DetailsActivity::class.java)
                    .putExtra("postId", id)

                startActivityForResult(
                    intent,
                    REQUEST_DETAILS
                )
            }
        }

        observe(vm.showDeleteCompleteToast) {
            it.getContentIfNotHandled()?.let {
                toast("Deleting post complete! - id: $it")
            }
        }
    }

    private fun setupPostList() {
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

            addOnItemTouchListener(
                RecyclerViewItemClickListener(
                    this@MainActivity,
                    this,
                    { _, pos ->
                        postAdapter.getItemAt(pos)?.let { post ->
                            vm.clickPostItem(post)
                        }
                    })
            )
        }

        observe(vm.posts) {
            val isEmpty = postAdapter.isEmpty()
            postAdapter.submitList(it)

            if (isEmpty) {
                linearLayoutManager.scrollToPosition(0)
            }
        }

        observe(vm.state) {
            postAdapter.networkStateChanged(it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(main_toolbar)
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
                        vm.notifyPostDeleted(id)
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