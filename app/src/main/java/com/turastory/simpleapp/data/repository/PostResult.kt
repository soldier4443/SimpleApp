package com.turastory.simpleapp.data.repository

import androidx.paging.PagedList
import com.turastory.simpleapp.data.source.NetworkState
import com.turastory.simpleapp.vo.Post
import io.reactivex.Observable

data class PostResult(
    val pagedList: Observable<PagedList<Post>>,
    val networkState: Observable<NetworkState>
)