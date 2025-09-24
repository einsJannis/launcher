package dev.einsjannis.uindex.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.uindex.uIndex
import dev.einsjannis.uindex.data.AppRepository
import dev.einsjannis.uindex.ui.components.CategoryViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

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
                val uIndex = checkNotNull(this[APPLICATION_KEY]) as uIndex
                ListViewModel(uIndex.appRepository)
            }
        }
    }
}