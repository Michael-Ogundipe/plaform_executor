package com.example.plaform_executor

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.os.BatteryManager
import android.content.Context

class MainActivity : FlutterActivity() {
    private val CHANNEL = "io.flutterengineering.battery/methods"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            if (call.method == "getBatteryLevel") {
                val batteryLevel = getBatteryLevel()
                if (batteryLevel != null) {
                    result.success(batteryLevel)
                } else {
                    result.error(
                        "UNAVAILABLE",
                        "Battery level not available",
                        null
                    )
                }
            } else {
                result.notImplemented()
            }
        }

    }

    private fun getBatteryLevel(): Int? {
        val batteryManager =
            getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )
    }

}