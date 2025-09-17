package dev.einsjannis.launcher.data

import dev.einsjannis.launcher.db.App
import dev.einsjannis.launcher.db.Database
import dev.einsjannis.launcher.domain.FavoriteInfo

class AppInfoSource(private val database: Database) {
    private val dao = database.appDao()
    private suspend fun getOrInsert(packageName: String): App {
        val app = dao.get(packageName)
        if (app!=null) return app
        dao.insert(packageName)
        return dao.get(packageName)!!
    }
    suspend fun newFavoriteInfo(): FavoriteInfo {
        return FavoriteInfo((dao.getGreatestFavorite()?.plus(1)) ?: 0)
    }
    suspend fun getFavoriteInfo(packageName: String): FavoriteInfo? {
        return getOrInsert(packageName).favorite?.let { FavoriteInfo(it) }
    }
    suspend fun isHidden(packageName: String): Boolean {
        return getOrInsert(packageName).isHidden
    }
    suspend fun setFavoriteInfo(packageName: String, info: FavoriteInfo?) {
        dao.setFavorite(packageName, info?.position)
    }
    suspend fun setHidden(packageName: String, hidden: Boolean) {
        dao.setHidden(packageName, hidden)
    }
}