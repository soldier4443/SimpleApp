package com.turastory.simpleapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.turastory.simpleapp.TestSchedulerRule
import org.junit.Rule
import org.junit.rules.TestRule

open class TestBase {
    // Make synchronous
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testSchedulerRule = TestSchedulerRule()
}