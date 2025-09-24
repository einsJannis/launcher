package dev.einsjannis.uindex.data

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log

class AppsSource(private val packageManager: PackageManager) {
    val apps: List<ApplicationInfo> get() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        return packageManager.queryIntentActivities(intent, 0)
            .distinctBy { it.activityInfo.applicationInfo.packageName }
            .map { it.activityInfo.applicationInfo }
    }
    fun getIcon(app: ApplicationInfo): Drawable? {
        return packageManager.getDrawable(app.packageName, app.icon, app)
    }
    fun getLabel(app: ApplicationInfo): String {
        return packageManager.getApplicationLabel(app).toString()
    }
}
