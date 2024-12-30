package com.devara.benchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Thread.sleep


/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class CatFactStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() {
        benchmarkRule.measureRepeated(
            packageName = "jp.speakbuddy.edisonandroidexercise",
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD,
            setupBlock = {
                pressHome()
                startActivityAndWait()
                val button = device.findObject(By.res("button_fact"))
                button.click()
                device.wait(Until.findObject(By.res("fact_card")), 5000);
                sleep(2000)
            }
        ) {
            killApp("jp.speakbuddy.edisonandroidexercise")
            startActivityAndWait()
        }
    }

    fun killApp(packageName: String) {
        try {
            Runtime.getRuntime().exec("adb shell am force-stop $packageName")
            println("App $packageName has been killed.")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}