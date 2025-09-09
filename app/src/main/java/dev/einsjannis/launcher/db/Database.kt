package dev.einsjannis.launcher.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [App::class], version = 1)
abstract class Database(): RoomDatabase() {
    abstract fun appDao(): AppDao
}