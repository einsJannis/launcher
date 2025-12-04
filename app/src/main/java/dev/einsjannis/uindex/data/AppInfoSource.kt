package dev.einsjannis.uindex.data

import dev.einsjannis.uindex.db.App
import dev.einsjannis.uindex.db.Database
import dev.einsjannis.uindex.domain.FavoriteInfo

class AppInfoSource(private val database: Database) {
    private val dao = database.appDao()
    suspend fun newFavoriteInfo(): FavoriteInfo {
        return FavoriteInfo((dao.getGreatestFavorite()?.plus(1)) ?: 0)
    }

    suspend fun getFavoriteInfo(packageName: String): FavoriteInfo? {
        return dao.get(packageName)?.favorite?.let { FavoriteInfo(it) }
    }

    suspend fun isHidden(packageName: String): Boolean {
        val app = dao.get(packageName)
        if (app == null) return false
        return app.isHidden
    }

    suspend fun setFavoriteInfo(packageName: String, info: FavoriteInfo?) {
        dao.setFavorite(packageName, info?.position)
    }

    suspend fun setHidden(packageName: String, hidden: Boolean) {
        dao.setHidden(packageName, hidden)
    }
}