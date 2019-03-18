package com.turastory.simpleapp.ui.postedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.toast
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.activity_post_edit.*

class PostEditActivity : AppCompatActivity() {

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit)

        intent.getParcelableExtra<Post>("post")?.let {
            this.post = it
            Log.e("testtest", "Post received - [${it.title}]: [${it.body}]")

            setupToolbar()
            setupEditText(it)
            post_edit_title.requestFocus()
        } ?: run {
            toast("Error occurred!")
            cancelEdit()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(post_edit_toolbar)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupEditText(it: Post) {
        post_edit_title.setText(it.title, TextView.BufferType.EDITABLE)
        post_edit_body.setText(it.body, TextView.BufferType.EDITABLE)
    }

    override fun onBackPressed() {
        cancelEdit()
    }

    override fun onSupportNavigateUp(): Boolean {
        cancelEdit()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_edit_complete -> {
                completeEdit(gatherData())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun gatherData() = Post(
        this.post.id,
        this.post.userId,
        post_edit_title.text.toString(),
        post_edit_body.text.toString())

    private fun completeEdit(post: Post) {
        setResult(Activity.RESULT_OK, Intent().putExtra("post", post))
        finish()
    }

    private fun cancelEdit() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
