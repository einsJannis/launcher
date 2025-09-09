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
    suspend fun toggleFavorite(packageName: String) {
        val app = getOrInsert(packageName)
        app.favorite = (dao.getGreatestFavorite() ?: 0) + 1
        dao.update(app)
    }
    suspend fun toggleHidden(packageName: String) {
        val app = getOrInsert(packageName)
        app.isHidden = !app.isHidden
        dao.update(app)
    }
}