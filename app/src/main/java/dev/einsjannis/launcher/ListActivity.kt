package dev.einsjannis.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.ui.theme.LauncherTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val apps = (application as Launcher).apps
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxWidth()) {
                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    val offset = remember { apps.categories.map { 0f }.toMutableStateList() }
                    apps.Element(context = applicationContext, listState = listState)
                    apps.ElementScrollBar(
                        modifier = Modifier.align(Alignment.CenterEnd).scrollBarAction(apps, listState, offset, coroutineScope),
                    )
                }
            }
        }
    }


    private fun Modifier.scrollBarAction(
        apps: Apps,
        listState: LazyListState,
        offset: SnapshotStateList<Float>,
        coroutineScope: CoroutineScope
    ): Modifier {
        return this.pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, _ ->
                    for (i in 0..apps.categories.lastIndex) offset[i] = 0f
                    val y = change.position.y
                    val index = (y / 95).toInt()
                        .coerceIn(0, apps.categories.lastIndex)
                    offset[index] = 50f
                    coroutineScope.launch {
                        listState.animateScrollToItem(
                            apps.categorized.indexOf(
                                apps.categorized.find {
                                    it is HeaderItem && it.text == apps.categories[index].toString()
                                })
                        )
                    }
                },
                onDragEnd = {
                    for (i in 0..apps.categories.lastIndex) offset[i] = 0f
                }
            )
        }
    }
}