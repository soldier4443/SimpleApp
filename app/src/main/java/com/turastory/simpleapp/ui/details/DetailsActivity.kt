package com.turastory.simpleapp.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.turastory.simpleapp.R
import com.turastory.simpleapp.base.BaseActivity
import com.turastory.simpleapp.data.source.State
import com.turastory.simpleapp.ext.hide
import com.turastory.simpleapp.ext.injector
import com.turastory.simpleapp.ext.show
import com.turastory.simpleapp.ext.toast
import com.turastory.simpleapp.ui.edit.EditPostActivity
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    companion object {
        const val REQUEST_POST_EDIT = 101
    }

    @Inject
    lateinit var vm: DetailsViewModel
    private val commentAdapter: CommentAdapter = CommentAdapter()

    override fun inject() {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val postId = intent.getIntExtra("postId", -1)

        if (postId != -1) {
            setupToolbar()
            loadPostDetails(postId)
            setupDialog()
            setupNavigation()
        } else {
            toast("Error occurred!")
            finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(details_toolbar)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun loadPostDetails(postId: Int) {
        vm.initPostId(postId)

        vm.postDetails.observe(this, Observer { post ->
            showPostDetails(post)
        })

        vm.comments.observe(this, Observer { comments ->
            showComments(comments)
        })

        vm.state.observe(this, Observer { networkState ->
            when (networkState.state) {
                State.LOADING -> showLoadingPage()
                State.LOADED -> hideLoadingPage()
                State.FAILED -> hideLoadingPage()
            }
        })
    }

    private fun showPostDetails(post: Post) {
        post_details_title.text = post.title
        post_details_body.text = post.body
    }

    private fun showComments(comments: List<Comment>) {
        comments_list.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            setHasFixedSize(true)

            commentAdapter.setComments(comments)
        }
    }

    private fun showLoadingPage() = loading_page.show()

    private fun hideLoadingPage() = loading_page.hide()

    private fun setupNavigation() {
        vm.navigateBackToMain.observe(this, Observer {
            it.getContentIfNotHandled()?.let { deletedPostId ->
                setResult(
                    Activity.RESULT_OK,
                    Intent().putExtra("deletedPostId", deletedPostId)
                )
                finish()
            }
        })

        vm.navigateToEditPost.observe(this, Observer {
            it.getContentIfNotHandled()?.let { post ->
                val intent = Intent(this, EditPostActivity::class.java)
                    .putExtra("post", post)

                startActivityForResult(
                    intent,
                    REQUEST_POST_EDIT
                )
            }
        })
    }

    private fun setupDialog() {
        vm.showDeleteConfirmDialog.observe(this, Observer {
            it.runIfNotHandled { showConfirmDialog() }
        })

        vm.showUpdateCompleteToast.observe(this, Observer {
            it.runIfNotHandled { toast("Update post complete!") }
        })
    }

    private fun showConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete post")
            .setMessage("Are you sure want to delete the post?")
            .setPositiveButton("Yes") { _, _ ->
                vm.deletePost()
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_delete_post -> {
                vm.clickDeletePost()
                true
            }
            R.id.menu_edit_post -> {
                vm.clickEditPost()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_POST_EDIT) {
            handlePostEditResult(resultCode, data)
        }
    }

    private fun handlePostEditResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.getParcelableExtra<Post>("post")?.let {
                    vm.updatePost(it)
                }
            }
            Activity.RESULT_CANCELED -> {
                toast("Editing canceled.")
            }
            else -> throw IllegalStateException("Illegal result code")
        }
    }
}