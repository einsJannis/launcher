package dev.einsjannis.launcher.data

import dev.einsjannis.launcher.domain.App
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
        appInfoSource.toggleFavorite(app.packageName)
        load()
    }
    suspend fun toggleHidden(app: App) {
        appInfoSource.toggleHidden(app.packageName)
        load()
    }
}