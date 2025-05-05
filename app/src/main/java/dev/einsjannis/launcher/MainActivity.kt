package dev.einsjannis.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.list.AppItem
import dev.einsjannis.launcher.list.CalendarItem
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.list.TimeItem
import dev.einsjannis.launcher.ui.theme.LauncherTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appItems = AppInfo.allApps(applicationContext).map { AppItem(it) }.toMutableList()
        val firstChars = mutableListOf<Char>()
        val items = appItems.zipWithNext().flatMap { (current, next) ->
            val nextStartChar = next.app.name.first()
            if (current.app.name.first() == nextStartChar) {
                listOf(current)
            } else {
                firstChars.add(nextStartChar)
                listOf(current, HeaderItem(nextStartChar.toString()))
            }
        }.toMutableList()
        items.addLast(appItems.last())
        val firstChar = appItems.first().app.name.first()
        firstChars.addFirst(firstChar)
        items.addFirst(HeaderItem(firstChar.toString()))
        items.addFirst(CalendarItem())
        items.addFirst(TimeItem())
        items.addFirst(SpaceItem())
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn (state = listState, modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                            items(items) { item ->
                                item.Element(applicationContext)
                            }
                        }
                    }
                    val offset = remember { firstChars.map { 0f }.toMutableStateList() }
                    LazyColumn(modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDrag = { change, _ ->
                                        for (i in 0..firstChars.lastIndex) offset[i] = 0f
                                        val y = change.position.y
                                        val index = (y / 95).toInt()
                                            .coerceIn(0, firstChars.lastIndex)
                                        offset[index] = 50f
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(items.indexOf(items.find {
                                                it is HeaderItem && it.text == firstChars[index].toString()
                                            }))
                                        }
                                    },
                                    onDragEnd = {
                                        for (i in 0..firstChars.lastIndex) offset[i] = 0f
                                    }
                                )
                            },
                        userScrollEnabled = false) {
                        itemsIndexed(firstChars) { index, it ->
                            val animatedOffset by animateFloatAsState(offset[index])
                            Text(it.toString(), style = TextStyle(
                                fontSize = 22.sp,
                                color = Color.White,
                                shadow = Shadow(
                                    color = Color.Black,
                                    blurRadius = 7f
                                ),
                                textAlign = TextAlign.Center
                            ), modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            ).width(22.dp).offset { IntOffset(-animatedOffset.toInt(), 0) })
                        }
                    }
                }
            }
        }
    }
}
