package com.turastory.simpleapp.ui.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.jakewharton.rxbinding3.widget.textChanges
import com.turastory.simpleapp.R
import com.turastory.simpleapp.base.BaseActivity
import com.turastory.simpleapp.ext.injector
import com.turastory.simpleapp.ext.observe
import com.turastory.simpleapp.ext.plusAssign
import com.turastory.simpleapp.ext.toast
import com.turastory.simpleapp.vo.Post
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_post_edit.*
import javax.inject.Inject

class EditPostActivity : BaseActivity() {

    @Inject
    lateinit var vm: EditPostViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun inject() {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit)

        // TODO ViewModel로 상태 관리
        intent.getParcelableExtra<Post>("post")?.let { post ->
            vm.initViewModel(post)
            setupToolbar()
            setupEditText()

            observe(vm.navigateBackToDetails) {
                it.runIfNotHandled { cancel() }
            }

            observe(vm.navigateBackToDetailsWithData) {
                it.getContentIfNotHandled()?.let { post ->
                    completeEdit(post)
                }
            }
        } ?: run {
            toast("Error occurred!")
            cancel()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(post_edit_toolbar)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupEditText() {
        observe(vm.postData) { post ->
            post_edit_title.setText(post.title, TextView.BufferType.EDITABLE)
            post_edit_body.setText(post.body, TextView.BufferType.EDITABLE)
        }

        post_edit_title.requestFocus()

        compositeDisposable += post_edit_title.textChanges()
            .map { it.toString() }
            .subscribe(vm::updateTitle)

        compositeDisposable += post_edit_body.textChanges()
            .map { it.toString() }
            .subscribe(vm::updateBody)
    }

    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun completeEdit(post: Post) {
        setResult(Activity.RESULT_OK, Intent().putExtra("post", post))
        finish()
    }

    override fun onBackPressed() {
        cancel()
    }

    override fun onSupportNavigateUp(): Boolean {
        cancel()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_edit_complete -> {
                vm.clickEditComplete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
