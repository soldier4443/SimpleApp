package com.turastory.simpleapp.ui.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.toast

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val postId = intent.getIntExtra("postId", -1)

        if (postId != -1) {
            Log.e("testtest", "Received post id: [$postId]")
        } else {
            toast("Error occurred!")
            finish()
        }
    }
}