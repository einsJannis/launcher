package dev.einsjannis.uindex.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.uindex.uIndex
import dev.einsjannis.uindex.data.AppRepository
import dev.einsjannis.uindex.domain.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.xdrop.fuzzywuzzy.FuzzySearch

class SearchViewModel(val appRepository: AppRepository) : ViewModel() {
    private val _result: MutableStateFlow<List<App>> = MutableStateFlow(emptyList())
    val result: StateFlow<List<App>> = _result

    fun search(search: String) {
        viewModelScope.launch {
            _result.value = FuzzySearch.extractSorted(search, appRepository.apps.value.map { it.label }, 20)
                .flatMap { appRepository.apps.value.filter { inner -> inner.label == it.string } }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val uIndex = checkNotNull(this[APPLICATION_KEY]) as uIndex
                SearchViewModel(uIndex.appRepository)
            }
        }
    }
}