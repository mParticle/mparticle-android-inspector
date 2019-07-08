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
        assertEquals(categoryController.getItems().size, categoryController.categoryTitles.size)
        categoryController.getItems().forEachIndexed { i, event ->
            assertEquals(event, categoryController.categoryTitles[i])
        }
    }

}