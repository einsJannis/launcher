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

    val apps: List<AppInfo> = apps.sortedBy { it.name.uppercase() }

    val categories: List<Char> = this.apps.map { it.name.uppercase().first() }.distinct()

    val categorized: List<Item> = this.apps
        .map { AppItem(it) }
        .insertBetween { prev, next ->
            val prevChar = prev?.let { it.app.name.first().uppercase() }
            if (next == null) return@insertBetween null
            val nextChar = next.app.name.first().uppercase()
            if (prevChar != nextChar) HeaderItem(nextChar) else null
        }

    override val items: List<Item> get() = listOf(SpaceItem()) + categorized + SpaceItem()

    @Composable
    override fun Element(menu: MutableState<Menu?>, offset: MutableState<Int>) {
        val listState = rememberLazyListState()
        LaunchedEffect(offset.value) {
            Log.d("Offset", "Offset to scroll to ${offset.value}")
            val target = items.indexOfFirst {
                (it as? HeaderItem)?.text?.uppercase() == categories[offset.value].uppercase()
            }
            Log.d("Offset", "Target to scroll to $target")
            listState.animateScrollToItem(target, scrollOffset = -listState.layoutInfo.viewportSize.height/3)
        }
        List(menu, listState)
    }

}