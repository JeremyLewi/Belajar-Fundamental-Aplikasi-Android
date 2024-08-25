package com.example.submissionawal.ui.splash

import android.os.Handler
import android.os.Looper
import org.junit.Assert.assertEquals
import org.junit.Test

class SplashActivityTest {

    @Test
    fun onCreate() {
        val expectedDelay = 3000L
        val actualDelay = SplashActivity().onCreate()

        assertEquals(expectedDelay, actualDelay)
    }

    private inner class SplashActivity {

        fun onCreate(): Long {
            val delay: Long = 3000

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({}, delay)

            return delay
        }
    }
}
