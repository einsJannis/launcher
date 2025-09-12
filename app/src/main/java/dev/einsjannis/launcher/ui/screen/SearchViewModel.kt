package dev.einsjannis.launcher.ui.screen

import androidx.compose.foundation.text.input.TextFieldState
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(val appRepository: AppRepository) : ViewModel() {
    private val _result: MutableStateFlow<List<App>> = MutableStateFlow(emptyList())
    val result: StateFlow<List<App>> = _result

    fun search(search: String) {
        viewModelScope.launch {
            _result.value = appRepository.apps.value.filter { it.label.contains(search, ignoreCase = true) }.sortedBy { it.label.uppercase() }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as Application
                SearchViewModel(application.appRepository)
            }
        }
    }
}