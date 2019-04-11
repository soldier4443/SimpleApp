package com.turastory.simpleapp.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.ui.Event
import com.turastory.simpleapp.ui.SimpleEvent
import com.turastory.simpleapp.vo.Post
import javax.inject.Inject

// 상태 관리가 안되는 것 같지만 일단 동작함
class EditPostViewModel @Inject constructor() : ViewModel() {

    private val _postData = MutableLiveData<Post>()
    val postData: LiveData<Post>
        get() = _postData

    private val _navigateBackToDetailsWithData = MutableLiveData<Event<Post>>()
    val navigateBackToDetailsWithData: LiveData<Event<Post>>
        get() = _navigateBackToDetailsWithData

    private val _navigateBackToDetails = MutableLiveData<SimpleEvent>()
    val navigateBackToDetails: LiveData<SimpleEvent>
        get() = _navigateBackToDetails

    private lateinit var post: Post
    private var title = ""
    private var body = ""

    fun initViewModel(post: Post) {
        this.post = post
        this.title = post.title
        this.body = post.body
        _postData.value = post
    }

    fun clickEditComplete() {
        _navigateBackToDetailsWithData.value = Event(
            Post(
                post.id,
                post.userId,
                title,
                body
            )
        )
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateBody(body: String) {
        this.body = body
    }
}