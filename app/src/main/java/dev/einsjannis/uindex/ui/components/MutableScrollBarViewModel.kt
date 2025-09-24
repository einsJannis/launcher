package dev.einsjannis.uindex.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.uindex.ui.screen.ListViewModel
import dev.einsjannis.uindex.ui.screen.Screen
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.roundToInt

class MutableScrollBarViewModel(
    val list: ListViewModel,
    val screen: MutableState<Screen>
) : ScrollBarViewModel() {
    val scrollYPos: MutableState<Float> = mutableFloatStateOf(0f)
    override val isHeld: MutableState<Boolean> = mutableStateOf(false)
    override val index: State<Int> = derivedStateOf {
        (scrollYPos.value*list.categoryIndices.value.size).roundToInt()
    }
    val handlePointerInput: PointerInputScope.() -> Unit = {
        val height = this.size.height
        viewModelScope.launch { detectDragGestures(
            onDrag = { change, dragAmount ->
                scrollYPos.value = (change.position.y / height).coerceIn(0f, 1f)
            },
            onDragStart = {
                if (screen.value != Screen.LIST)
                    screen.value = Screen.LIST
                isHeld.value = true
            },
            onDragEnd = {
                isHeld.value = false
            },
            onDragCancel = {
                isHeld.value = false
            }
        ) }
    }
    fun handleClick(index: Int): () -> Unit = {
        if (screen.value != Screen.LIST)
            screen.value = Screen.LIST
        scrollYPos.value = (index.toFloat() / list.categoryIndices.value.size.toFloat())
    }
    fun offset(index: Int): Float {
        val distance = (index.toFloat() / list.categoryIndices.value.size.toFloat()) - scrollYPos.value
        val d = distance*list.categoryIndices.value.size
        return 1.2f.pow(-(d*d))*80f
    }
    companion object {
        fun Factory(list: ListViewModel, screen: MutableState<Screen>): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    MutableScrollBarViewModel(list, screen)
                }
            }
        }
    }
}