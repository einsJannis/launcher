package dev.einsjannis.uindex.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.einsjannis.uindex.data.AppRepository
import dev.einsjannis.uindex.domain.App
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.Normalizer

sealed class CategoryViewModel(private val repository: AppRepository, val filter: (App) -> Boolean, val title: String) : ViewModel() {
    val apps: StateFlow<List<App>> = repository.apps
        .map { app -> app.filter(filter).filter { !it.isHidden } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    companion object {
        fun Char.stripAccents(): Char {
            val normalized = Normalizer.normalize(this.toString(), Normalizer.Form.NFD)
            return normalized.first()
        }
    }

    class Letter(repository: AppRepository, char: Char) : CategoryViewModel(repository, {it.label.first().uppercaseChar().stripAccents()==char.uppercaseChar()}, char.uppercase())
    class Symbol(repository: AppRepository) : CategoryViewModel(repository, {!it.label.first().isLetter()}, "$")
}