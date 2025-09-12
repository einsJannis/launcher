package dev.einsjannis.launcher.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.launcher.Application
import dev.einsjannis.launcher.data.AppRepository
import dev.einsjannis.launcher.domain.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: AppRepository) : ViewModel() {
    val apps: StateFlow<List<App>> = repository.apps.map { apps -> apps.filter { it.isFavorite }.sortedBy { it.favoriteInfo!!.position } }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun moveUp(app: App) {
        val apps = apps.value
        val index = apps.indexOf(app)
        Log.d("MoveDebug", "Index is $index")
        if (index == -1) return
        val above = apps.getOrNull(index-1)
        if (above == null) return
        viewModelScope.launch {
            repository.setFavoriteInfo(app, above.favoriteInfo)
            repository.setFavoriteInfo(above, app.favoriteInfo)
        }
    }

    fun moveDown(app: App) {
        val apps = apps.value
        val index = apps.indexOf(app)
        if (index == -1) return
        val above = apps.getOrNull(index+1)
        if (above == null) return
        viewModelScope.launch {
            repository.setFavoriteInfo(app, above.favoriteInfo)
            repository.setFavoriteInfo(above, app.favoriteInfo)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as Application
                FavoritesViewModel(application.appRepository)
            }
        }
    }
}