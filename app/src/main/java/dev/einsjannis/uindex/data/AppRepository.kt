package dev.einsjannis.uindex.data

import dev.einsjannis.uindex.domain.App
import dev.einsjannis.uindex.domain.FavoriteInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppRepository(private val appSource: AppsSource, private val appInfoSource: AppInfoSource) {
    private val _apps: MutableStateFlow<List<App>> = MutableStateFlow(emptyList())
    val apps: StateFlow<List<App>> = _apps

    suspend fun load() {
        _apps.value = appSource.apps.map {
            App(
                it.packageName,
                appSource.getLabel(it),
                appSource.getIcon(it),
                appInfoSource.getFavoriteInfo(it.packageName),
                appInfoSource.isHidden(it.packageName)
            )
        }
    }
    suspend fun toggleFavorite(app: App) {
        val oldInfo = app.favoriteInfo
        val newInfo = if (oldInfo == null) appInfoSource.newFavoriteInfo() else null
        appInfoSource.setFavoriteInfo(app.packageName, newInfo)
        load()
    }
    suspend fun setFavoriteInfo(app: App, favoriteInfo: FavoriteInfo?) {
        appInfoSource.setFavoriteInfo(app.packageName, favoriteInfo)
        load()
    }
    suspend fun toggleHidden(app: App) {
        val oldState = app.isHidden
        val newState = !oldState
        appInfoSource.setHidden(app.packageName, newState)
        load()
    }
    suspend fun setHidden(app: App, isHidden: Boolean) {
        appInfoSource.setHidden(app.packageName, isHidden)
        load()
    }

}