package dev.einsjannis.launcher

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.list.AppItem
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.menu.Menu

class Apps(backing: List<AppInfo>) {

    val categories: List<Char> = backing.map { it.name.first() }.distinct()

    val categorized: List<Item> = backing.map { AppItem(it) }.insertBetween { prev, next ->
            val prevChar = prev?.let { it.app.name.first() }
            if (next == null) return@insertBetween null
            val nextChar = next.app.name.first()
            if (prevChar != nextChar) HeaderItem(nextChar.toString()) else null
        }

    @Composable
    fun Element(context: Context, listState: LazyListState = LazyListState(), popUp: MutableState<Menu?>) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn (state = listState, modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                items(categorized) { item ->
                    item.Element(context, popUp)
                }
            }
        }
    }

    @Composable
    fun ElementScrollBar(
        modifier: Modifier,
        offset: SnapshotStateList<Float> = remember { categories.map { 0f }.toMutableStateList() }
    ) {
        LazyColumn(userScrollEnabled = false, modifier = modifier) {
            itemsIndexed(categories) { index, it ->
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