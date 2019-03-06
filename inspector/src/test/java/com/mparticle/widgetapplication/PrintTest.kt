package com.mparticle.widgetapplication

import com.mparticle.inspector.utils.printClass
import junit.framework.Assert.assertNotNull
import org.junit.Test

class PrintTest {

   @Test
    fun testParsing() {
       val clas = Something().printClass()
       assertNotNull(clas)
   }


    class Something {
        var map = mapOf("hello" to "world", "hey" to "sommmeme")
    }
}