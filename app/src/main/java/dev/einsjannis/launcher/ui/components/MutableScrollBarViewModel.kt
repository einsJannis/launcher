package dev.einsjannis.launcher.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import dev.einsjannis.launcher.ui.screen.ListViewModel
import dev.einsjannis.launcher.ui.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

class MutableScrollBarViewModel(
    val list: ListViewModel,
    val controller: NavController
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
                if (controller.currentBackStackEntry?.destination?.route != Screen.LIST.toString())
                    controller.navigate(Screen.LIST.toString())
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
    fun offset(index: Int): Float {
        val distance = (index.toFloat() / list.categoryIndices.value.size.toFloat()) - scrollYPos.value
        val d = distance*list.categoryIndices.value.size
        return 1.2f.pow(-(d*d))*80f
    }
    companion object {
        fun Factory(list: ListViewModel, controller: NavController): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    MutableScrollBarViewModel(list, controller)
                }
            }
        }
    }
}