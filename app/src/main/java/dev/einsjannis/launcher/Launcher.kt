package dev.einsjannis.launcher

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController

class Launcher : Application() {
    val favoriteScreen: FavoriteScreen = FavoriteScreen(listOf());
    val listScreen: ListScreen by lazy {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val res = packageManager.queryIntentActivities(intent, 0)
            .map { AppInfo(applicationContext, it) }
        return@lazy ListScreen(res)
    }
}