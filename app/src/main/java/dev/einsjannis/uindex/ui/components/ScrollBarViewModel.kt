package dev.einsjannis.uindex.ui.components

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

abstract class ScrollBarViewModel : ViewModel() {
    abstract val index: State<Int>
    abstract val isHeld: State<Boolean>
}