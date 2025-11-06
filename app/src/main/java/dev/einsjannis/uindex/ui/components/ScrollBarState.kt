package dev.einsjannis.uindex.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.einsjannis.uindex.ui.screen.ListViewModel
import dev.einsjannis.uindex.ui.screen.Screen
import kotlinx.coroutines.CoroutineScope

@Stable
interface ScrollBarState {
    val index: Int
    val isHeld: Boolean
}

@Composable
fun rememberScrollBarState(
    list: ListViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember { MutableScrollBarState(list, coroutineScope) }
