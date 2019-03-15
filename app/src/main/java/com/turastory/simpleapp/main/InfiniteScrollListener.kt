package com.turastory.simpleapp.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

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

        Log.e(MainActivity.TAG, "dx: [$dx], dy: [$dy], visible item: [${lastItemPosition()}]")
        Log.e(MainActivity.TAG, "Total Item Count: : ${layoutManager.itemCount}")
        doWork()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        fun interpret(state: Int) = when (state) {
            0 -> "IDLE"
            1 -> "DRAGGING"
            2 -> "SETTLING"
            else -> "NOTHING"
        }

        Log.e(MainActivity.TAG, "newState: ${interpret(newState)}, visible item: [${lastItemPosition()}]")
        Log.e(MainActivity.TAG, "Total Item Count: : ${layoutManager.itemCount}")
        doWork()
    }

    private fun doWork() {
        val maxCount = layoutManager.itemCount

        if (maxCount > 0 &&
            lastItemPosition() == maxCount - 1 &&
            (System.currentTimeMillis() - lastActiveTime) > activeDelay) {

            task()
            lastActiveTime = System.currentTimeMillis()
        }
    }

    private fun lastItemPosition() = layoutManager.findLastCompletelyVisibleItemPosition()
}