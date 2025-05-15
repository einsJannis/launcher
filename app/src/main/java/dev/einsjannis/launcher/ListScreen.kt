package dev.einsjannis.launcher

import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import dev.einsjannis.launcher.list.AppItem
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.menu.Menu

class ListScreen(apps: List<AppInfo>) : Screen() {
    override val name: String = "list"

    val categories: List<Char> = apps.map { it.name.first() }.distinct()

    val categorized: List<Item> = listOf(SpaceItem()) + apps.map { AppItem(it) }.insertBetween { prev, next ->
        val prevChar = prev?.let { it.app.name.first() }
        if (next == null) return@insertBetween null
        val nextChar = next.app.name.first()
        if (prevChar != nextChar) HeaderItem(nextChar.toString()) else null
    } + SpaceItem()

    override val items: List<Item> get() = categorized

    @Composable
    override fun Element(menu: MutableState<Menu?>, offset: MutableState<Int>) {
        val listState = rememberLazyListState()
        LaunchedEffect(offset.value) {
            Log.d("Offset", "Offset to scroll to ${offset.value}")
            val target = categorized.indexOfFirst {
                (it as? HeaderItem)?.text == categories[offset.value].toString()
            }
            Log.d("Offset", "Target to scroll to $target")
            listState.animateScrollToItem(target, scrollOffset = -listState.layoutInfo.viewportSize.height/3)
        }
        List(menu, listState)
    }

}