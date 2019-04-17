package com.turastory.simpleapp.ui.edit

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.ui.Event
import com.turastory.simpleapp.ui.SimpleEvent
import com.turastory.simpleapp.vo.Post
import javax.inject.Inject

// 상태 관리가 안되는 것 같지만 일단 동작함
class EditPostViewModel @Inject constructor() : ViewModel() {

    val postTitle = MutableLiveData<String>()

    val postBody = MutableLiveData<String>()

    private val _navigateBackToDetailsWithData = MutableLiveData<Event<Post>>()
    val navigateBackToDetailsWithData: LiveData<Event<Post>>
        get() = _navigateBackToDetailsWithData

    private val _navigateBackToDetails = MutableLiveData<SimpleEvent>()
    val navigateBackToDetails: LiveData<SimpleEvent>
        get() = _navigateBackToDetails

    private lateinit var post: Post

    fun initViewModel(post: Post) {
        this.post = post
        postTitle.postValue(post.title)
        postBody.postValue(post.body)
    }

    fun clickEditComplete() {
        val post = Post(
            post.id,
            post.userId,
            postTitle.value ?: "",
            postBody.value ?: ""
        )

        _navigateBackToDetailsWithData.postValue(Event(post))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setPost(post: Post) {
        this.post = post
    }
}