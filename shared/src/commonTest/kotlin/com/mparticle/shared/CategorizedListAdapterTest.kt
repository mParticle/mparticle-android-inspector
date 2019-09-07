package com.mparticle.shared

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CategorizedListAdapterTest {
    lateinit var viewControllerManager: ViewControllerManager

    @BeforeTest
    fun setup() {
        viewControllerManager = ViewControllerManager()
    }

    @Test
    fun testEmptyList() {
        val categoryController = viewControllerManager.categoryController
        assertEquals(categoryController.categoryTitles.size, categoryController.getEvents().size)
        categoryController.getEvents().forEachIndexed { i, event ->
            assertEquals(event, categoryController.categoryTitles[i])
        }
    }

}