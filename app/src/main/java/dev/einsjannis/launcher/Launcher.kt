package dev.einsjannis.launcher

import android.app.Application
import android.content.Context
import android.content.Intent

class Launcher : Application() {
    val apps: Apps
        get() {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val res = applicationContext.packageManager.queryIntentActivities(intent, 0)
                .map { AppInfo(applicationContext, it) }
                .sortedBy { it.name }
            return Apps(res)
        }
}