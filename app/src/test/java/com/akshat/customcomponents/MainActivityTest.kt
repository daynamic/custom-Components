package com.akshat.customcomponents

import androidx.core.view.isVisible
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshat.customcomponents.widgets.Alert
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `test click of Success`(){
        /*ActivityScenario.launch(MainActivity::class.java).onActivity{
            onView(withId(R.id.button)).perform(click())
            val alertSuccess = it.findViewById<Alert>(R.id.alert_message)
            assertThat(alertSuccess.isVisible).isEqualTo(true)
        }*/
        activityRule.scenario.onActivity {
            onView(withId(R.id.button)).perform(click())
            val alertSuccess = it.findViewById<Alert>(R.id.alert_message)
            assertThat(alertSuccess.isVisible).isEqualTo(true)
        }
    }
}