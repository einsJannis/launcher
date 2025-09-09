package dev.einsjannis.launcher.ui.screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.launcher.Application
import dev.einsjannis.launcher.data.AppRepository
import dev.einsjannis.launcher.ui.components.CategoryViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(private val repository: AppRepository) : ViewModel() {
    val allCategories = (('A'..'Z').map {
        CategoryViewModel.Letter(repository, it)
    } + CategoryViewModel.Symbol(repository))
    val categoryIndices: StateFlow<List<Int>> = combine(allCategories.map { it.apps }) { latest ->
        latest.withIndex().filter { it.value.isNotEmpty() }.map { it.index }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as Application
                ListViewModel(application.appRepository)
            }
        }
    }
}