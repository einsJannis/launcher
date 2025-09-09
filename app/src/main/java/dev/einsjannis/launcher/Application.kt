package dev.einsjannis.launcher

import android.app.Application
import androidx.room.Room
import dev.einsjannis.launcher.data.AppInfoSource
import dev.einsjannis.launcher.data.AppRepository
import dev.einsjannis.launcher.data.AppsSource
import dev.einsjannis.launcher.db.Database
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Application : Application() {
    lateinit var appsSource: AppsSource
    lateinit var appInfoSource: AppInfoSource
    lateinit var appRepository: AppRepository
    lateinit var database: Database
    val applicationScope = MainScope()
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, Database::class.java, "launcher").build()
        appsSource = AppsSource(packageManager)
        appInfoSource = AppInfoSource(database)
        appRepository = AppRepository(appsSource, appInfoSource)
        applicationScope.launch { appRepository.load() }
    }
}