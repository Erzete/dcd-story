package com.dicoding.dicodingstory.ui.login

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.dicodingstory.R
import com.dicoding.dicodingstory.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingstory.util.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun userLoginLogout_success() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), LoginActivity::class.java)
        ActivityScenario.launch<LoginActivity>(intent)

        onView(withId(R.id.ed_login_email)).perform(typeText("santai@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(typeText("Santai123"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
        onView(withId(R.id.loginButton)).perform(click())

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withText("Success!"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        onView(withText("Lanjut"))
            .inRoot(isDialog())
            .perform(click())

        onView(withId(R.id.action_logout)).check(matches(isDisplayed()))
        onView(withId(R.id.action_logout)).perform(click())
    }

    @Test
    fun userLoginLogout_failed() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), LoginActivity::class.java)
        ActivityScenario.launch<LoginActivity>(intent)

        onView(withId(R.id.ed_login_email)).perform(typeText("a@a"), closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(typeText("a"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
        onView(withId(R.id.loginButton)).perform(click())

        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withText("Failed!"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }
}