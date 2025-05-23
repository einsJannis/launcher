package dev.einsjannis.launcher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri

class AppInfo {
    val icon: Drawable
        get() = resolveInfo.loadIcon(pm)
    val name: String
        get() = resolveInfo.loadLabel(pm).toString()
    val launchIntent
        get() = pm.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
    val infoIntent
        get() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${resolveInfo.activityInfo.packageName}".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

    private val pm: PackageManager
    private val resolveInfo: ResolveInfo

    constructor(context: Context, packageName: String, activityName: String) {
        pm = context.packageManager
        var intent = Intent()
        intent.component = ComponentName(packageName, activityName)
        resolveInfo = pm.resolveActivity(intent, 0)!!
    }
    constructor(context: Context, resolveInfo: ResolveInfo) {
        this.pm = context.packageManager
        this.resolveInfo = resolveInfo
    }
}