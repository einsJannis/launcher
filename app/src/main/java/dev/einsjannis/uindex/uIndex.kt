package dev.einsjannis.uindex

import android.app.Application
import androidx.room.Room
import dev.einsjannis.uindex.data.AppInfoSource
import dev.einsjannis.uindex.data.AppRepository
import dev.einsjannis.uindex.data.AppsSource
import dev.einsjannis.uindex.db.Database
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class uIndex : Application() {
    lateinit var appsSource: AppsSource
    lateinit var appInfoSource: AppInfoSource
    lateinit var appRepository: AppRepository
    lateinit var database: Database
    val applicationScope = MainScope()
    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(applicationContext, Database::class.java, "launcher").build()
        appsSource = AppsSource(packageManager)
        appInfoSource = AppInfoSource(database)
        appRepository = AppRepository(appsSource, appInfoSource)
        applicationScope.launch { appRepository.load() }
    }
}