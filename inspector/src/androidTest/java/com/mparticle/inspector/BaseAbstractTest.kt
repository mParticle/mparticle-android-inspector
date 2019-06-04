package com.mparticle.inspector

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Before

abstract class BaseAbstractTest {

    lateinit var mContext: Context;

    @Before
    fun before() {
        mContext = InstrumentationRegistry.getContext();
    }
}