package com.wangguan.coroutine

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.wangguan.coroutine", appContext.packageName)
        println("aaa" + Thread.currentThread().name)
        Log.i("ddd", "bbb")

        GlobalScope.launch {
            println("" + Thread.currentThread().name)
            launch(Dispatchers.IO) {
            }
        }

        Thread.sleep(5000)
    }
}
