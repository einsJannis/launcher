package dev.einsjannis.launcher.ui.components

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.launcher.Application
import dev.einsjannis.launcher.data.AppRepository
import dev.einsjannis.launcher.domain.App
import dev.einsjannis.launcher.ui.screen.FavoritesViewModel
import kotlinx.coroutines.launch

class PopUpViewModel(val repository: AppRepository) : ViewModel() {
    val app: MutableState<App?> = mutableStateOf(null)
    val isOpen = derivedStateOf { app.value != null }

    fun openSettings(context: Context) {
        val packageName = app.value?.packageName
        if (packageName == null) return
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${packageName}".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            app.value?.also { repository.toggleFavorite(it) }
        }
    }

    fun toggleHidden() {
        viewModelScope.launch {
            app.value?.also { repository.toggleHidden(it) }
        }
    }

    fun open(app: App) {
        this.app.value = app
    }

    fun close() {
        app.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as Application
                PopUpViewModel(application.appRepository)
            }
        }
    }
}