package com.turastory.simpleapp.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.turastory.simpleapp.R
import com.turastory.simpleapp.ui.postedit.PostEditActivity
import com.turastory.simpleapp.ext.hide
import com.turastory.simpleapp.ext.show
import com.turastory.simpleapp.ext.toast
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.ext.android.inject

class DetailsActivity : AppCompatActivity(), DetailsContract.View {

    private val presenter: DetailsContract.Presenter by inject()
    private val commentAdapter: CommentAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val postId = intent.getIntExtra("postId", -1)

        if (postId != -1) {
            setupToolbar()

            presenter.setView(this)
            presenter.requestPostDetails(postId)
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
                presenter.requestDeletePost()
                true
            }
            R.id.menu_edit_post -> {
                presenter.editPost()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete post")
            .setMessage("Are you sure want to delete the post?")
            .setPositiveButton("Yes") { _, _ ->
                presenter.deletePost()
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    override fun showPostDetails(post: Post) {
        post_details_title.text = post.title
        post_details_body.text = post.body
    }

    override fun showComments(comments: List<Comment>) {
        comments_list.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            setHasFixedSize(true)

            commentAdapter.setComments(comments)
        }
    }

    override fun showLoadingPage() = loading_page.show()

    override fun hideLoadingPage() = loading_page.hide()

    override fun openEditPostView(post: Post) {
        startActivityForResult(
            Intent(this, PostEditActivity::class.java)
                .putExtra("post", post),
            DetailsContract.REQUEST_POST_EDIT
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DetailsContract.REQUEST_POST_EDIT) {
            handlePostEditResult(resultCode, data)
        }
    }

    private fun handlePostEditResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.getParcelableExtra<Post>("post")?.let {
                    toast("Update post complete!")
                    presenter.updatePost(it)
                }
            }
            Activity.RESULT_CANCELED -> {
                toast("Editing canceled.")
            }
            else -> throw IllegalStateException("Illegal result code")
        }
    }

    override fun completeDeletion(postId: Int) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra("deletedPostId", postId)
        )
        finish()
    }
}