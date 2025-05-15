package dev.einsjannis.launcher

import dev.einsjannis.launcher.list.AppItem
import dev.einsjannis.launcher.list.CalendarItem
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.list.TimeItem

class FavoriteScreen(favorites: List<AppInfo>) : Screen() {
    override val name: String = "favorite"
    override val items: List<Item> =
        listOf(SpaceItem(), TimeItem(), CalendarItem()) + favorites.map { AppItem(it) }
}