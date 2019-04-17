package com.turastory.simpleapp.ui.edit

import com.turastory.simpleapp.observeAndVerify
import com.turastory.simpleapp.ui.TestBase
import com.turastory.simpleapp.vo.Post
import org.junit.Test
import org.mockito.Mockito.`when` as whenever

class EditPostViewModelTest : TestBase() {

    private val viewModel: EditPostViewModel = EditPostViewModel()

    @Test
    fun initViewModelTest() {
        val post = Post(0, 0, "testTitle", "testBody")

        viewModel.initViewModel(post)

        observeAndVerify(viewModel.postTitle, "testTitle")
        observeAndVerify(viewModel.postBody, "testBody")
    }

    @Test
    fun clickEditCompleteTest() {
        viewModel.setPost(Post(0, 0, "", ""))

        viewModel.clickEditComplete()

        observeAndVerify(viewModel.navigateBackToDetailsWithData)
    }
}
