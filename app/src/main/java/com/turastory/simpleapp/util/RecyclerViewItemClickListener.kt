package com.turastory.simpleapp.util

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Detect click/long click event on each item in RecyclerView
 */
class RecyclerViewItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val onItemClick: ((View, Int) -> Unit)? = null,
    private val onItemLongClick: ((View, Int) -> Unit)? = null
) : RecyclerView.OnItemTouchListener {

    private var mGestureDetector: GestureDetector

    init {
        mGestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    onItemLongClick?.run {
                        recyclerView.findChildViewUnder(e.x, e.y)?.let {
                            this(it, recyclerView.getChildAdapterPosition(it))
                        }
                    }
                }
            })
    }

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        onItemClick?.run {
            recyclerView.findChildViewUnder(e.x, e.y)?.let {
                if (mGestureDetector.onTouchEvent(e)) {
                    this(it, recyclerView.getChildAdapterPosition(it))
                    return true // intercept the event!!
                }
            }
        }

        return false
    }

    override fun onTouchEvent(view: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}