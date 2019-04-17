package com.turastory.simpleapp.ui.details

import com.turastory.simpleapp.TestLoggerImpl
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.mock
import com.turastory.simpleapp.observeAndVerify
import com.turastory.simpleapp.ui.TestBase
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as whenever

class DetailsViewModelTest : TestBase() {

    private val repository: PostRepository = mock()
    private val viewModel: DetailsViewModel = DetailsViewModel(repository, TestLoggerImpl())

    @Test
    fun initPostIdTest() {
        // Given
        val id = 0
        whenever(repository.getPost(anyInt())).thenReturn(Single.just(mock()))
        whenever(repository.getComments(anyInt())).thenReturn(Single.just(mock()))

        // When
        viewModel.initPostId(id)

        // Then
        verify(repository).getPost(anyInt())
        verify(repository).getComments(anyInt())
        observeAndVerify(viewModel.postTitle)
        observeAndVerify(viewModel.postBody)
        observeAndVerify(viewModel.comments)
    }

    @Test
    fun clickDeletePostTest() {
        viewModel.clickDeletePost()

        observeAndVerify(viewModel.showDeleteConfirmDialog)
    }

    @Test
    fun clickEditPostTest() {
        viewModel.setPost(Post(0, 0, "", ""))

        viewModel.clickEditPost()

        observeAndVerify(viewModel.navigateToEditPost)
    }

    @Test
    fun updatePostTest() {
        val postData = Post(0, 0, "title", "body")
        whenever(repository.updatePost(postData)).thenReturn(Completable.complete())

        viewModel.updatePost(postData)

        verify(repository).updatePost(postData)
        observeAndVerify(viewModel.postTitle)
        observeAndVerify(viewModel.postBody)
        observeAndVerify(viewModel.showUpdateCompleteToast)
    }

    @Test
    fun deletePostTest() {
        whenever(repository.deletePost(anyInt())).thenReturn(Completable.complete())

        viewModel.deletePost()

        verify(repository).deletePost(anyInt())
        observeAndVerify(viewModel.navigateBackToMain)
    }
}