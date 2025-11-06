package dev.einsjannis.uindex.ui.components

import android.util.Log
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitVerticalPointerSlopOrCancellation
import androidx.compose.foundation.gestures.awaitVerticalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.PointerType
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.einsjannis.uindex.ui.screen.ListViewModel
import dev.einsjannis.uindex.ui.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.roundToInt

class MutableScrollBarState internal constructor(
    val list: ListViewModel,
    val coroutineScope: CoroutineScope
) : ScrollBarState {
    val scrollYPos: MutableState<Float> = mutableFloatStateOf(0f)
    val isHeldState: MutableState<Boolean> = mutableStateOf(false)
    override val isHeld: Boolean by isHeldState
    override val index: Int by derivedStateOf {
        (scrollYPos.value*list.categoryIndices.value.size).roundToInt()
    }
    val handlePointerInput: suspend PointerInputScope.() -> Unit = {
        val height = this.size.height
        awaitEachGesture {
            val down = awaitFirstDown()
            Log.d("aaaaa","tagged")
            isHeldState.value = true
            scrollYPos.value = (down.position.y / height).coerceIn(0f,1f)
            awaitVerticalPointerSlopOrCancellation(down.id, down.type) { change, overSlop ->
                scrollYPos.value = (change.position.y / height).coerceIn(0f,1f)
            }
            isHeldState.value = false
        }
    }
    fun offset(index: Int): Float {
        val distance = (index.toFloat() / list.categoryIndices.value.size.toFloat()) - scrollYPos.value
        val d = distance*list.categoryIndices.value.size
        return 1.2f.pow(-(d*d))*80f
    }
}