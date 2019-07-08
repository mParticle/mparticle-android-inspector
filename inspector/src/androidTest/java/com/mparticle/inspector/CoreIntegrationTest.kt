package com.mparticle.inspector

import android.content.Context
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.mparticle.SdkListener
import com.mparticle.shared.utils.Mutable
import junit.framework.Assert.*
import org.junit.Test

class CoreIntegrationTest: BaseAbstractTest() {

    @Test
    fun testSimpleRoundTrip() {
        val apiNameResult = Mutable<String?>(null)
        val argumentsResult = Mutable<List<Any>?>(null)
        val isExternalResult = Mutable<Boolean>(false)
        MParticle.addListener(mContext, object: SdkListener() {

            override fun onApiCalled(apiName: String, objects: MutableList<Any>, isExternal: Boolean) {
                apiNameResult.value = apiName
                argumentsResult.value = objects
                isExternalResult.value = isExternal
            }
        });
        MParticle::class.java.getMethod("reset", Context::class.java)
                .apply {
                    invoke(null, mContext)
                }

        assertNotNull(apiNameResult.value);
        assertEquals("MParticle.reset()", apiNameResult.value)
        assertEquals(1, argumentsResult.value?.size)
        assertEquals(mContext, argumentsResult.value?.get(0))
        assertTrue(isExternalResult.value)

    }
}