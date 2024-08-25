package com.example.submissionawal.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.submissionawal.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(SplashActivity::class.java)


    @Test
    fun splashActivityDelay() {
        val expectedDelay = 3000L

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            onView(withText(R.string.app_name)).check(matches(isDisplayed()))
        }, expectedDelay)
    }

}
