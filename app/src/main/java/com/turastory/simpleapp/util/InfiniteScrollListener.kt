package com.turastory.simpleapp.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Scroll listener for infinite scroll.
 * Use delay to prevent multiple omission of events
 */
class InfiniteScrollListener(private val layoutManager: LinearLayoutManager,
                             private val activeDelay: Int = 500,
                             private val task: () -> Unit = {})
    : RecyclerView.OnScrollListener() {

    private var lastActiveTime: Long = System.currentTimeMillis()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        checkBottomReached()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        checkBottomReached()
    }

    private fun checkBottomReached() {
        val maxCount = layoutManager.itemCount

        if (lastItemPosition() == maxCount - 1 &&
            maxCount > 0 &&
            (System.currentTimeMillis() - lastActiveTime) > activeDelay) {

            task()
            lastActiveTime = System.currentTimeMillis()
        }
    }

    private fun lastItemPosition() = layoutManager.findLastCompletelyVisibleItemPosition()
}