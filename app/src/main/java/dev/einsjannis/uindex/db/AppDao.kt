package dev.einsjannis.uindex.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {
    @Query("SELECT * FROM app")
    suspend fun getAll(): List<App>
    @Query("SELECT * FROM app WHERE app.package_name = :packageName")
    suspend fun get(packageName: String): App?
    @Query("SELECT favorite FROM app WHERE favorite = (SELECT MAX(favorite) FROM app)")
    suspend fun getGreatestFavorite(): Int?
    @Query("INSERT INTO app(package_name) VALUES(:packageName)")
    suspend fun insert(packageName: String)
    @Query("REPLACE INTO app(favorite, package_name) VALUES(:favorite, :packageName)")
    suspend fun setFavorite(packageName: String, favorite: Int?)
    @Query("REPLACE INTO app(is_hidden, package_name) VALUES(:isHidden, :packageName)")
    suspend fun setHidden(packageName: String, isHidden: Boolean)
    @Update
    suspend fun update(app: App)
}