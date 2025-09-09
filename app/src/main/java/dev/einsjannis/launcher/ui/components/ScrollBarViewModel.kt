package dev.einsjannis.launcher.ui.components

import androidx.compose.runtime.State
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.lifecycle.ViewModel

abstract class ScrollBarViewModel : ViewModel() {
    abstract val index: State<Int>
    abstract val isHeld: State<Boolean>
}