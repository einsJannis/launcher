package dev.einsjannis.launcher.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.einsjannis.launcher.data.AppRepository
import dev.einsjannis.launcher.domain.App
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class CategoryViewModel(private val repository: AppRepository, val filter: (App) -> Boolean, val title: String) : ViewModel() {
    val apps: StateFlow<List<App>> = repository.apps.map { it.filter(filter) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    class Letter(repository: AppRepository, char: Char) : CategoryViewModel(repository, {it.label.first().uppercaseChar()==char.uppercaseChar()}, char.uppercase())
    class Symbol(repository: AppRepository) : CategoryViewModel(repository, {!it.label.first().isLetter()}, "$")
}