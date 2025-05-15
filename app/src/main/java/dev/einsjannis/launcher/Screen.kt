package dev.einsjannis.launcher

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.menu.Menu

abstract class Screen {

    abstract val name: String

    abstract val items: List<Item>

    fun NavGraphBuilder.composable(menu: MutableState<Menu?>, offset: MutableState<Int>) {
        composable(this@Screen.name) {
            Element(menu, offset)
        }
    }

    @Composable
    open fun Element(menu: MutableState<Menu?>, offset: MutableState<Int>) {
        val listState = rememberLazyListState()
        List(menu, listState)
    }

    @Composable
    fun List(menu: MutableState<Menu?>, listState: LazyListState) {
        LazyColumn(state = listState) {
            items(items) { item ->
                item.Element(menu = menu)
            }
        }
    }

}