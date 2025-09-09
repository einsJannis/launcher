package dev.einsjannis.launcher.data

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

class AppsSource(private val packageManager: PackageManager) {
    val apps: List<ApplicationInfo> = buildList {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        packageManager.queryIntentActivities(intent, 0)
            .forEach { add(it.activityInfo.applicationInfo) }
    }
    fun getIcon(app: ApplicationInfo): Drawable? {
        return packageManager.getDrawable(app.packageName, app.icon, app)
    }
    fun getLabel(app: ApplicationInfo): String {
        return packageManager.getApplicationLabel(app).toString()
    }
}
