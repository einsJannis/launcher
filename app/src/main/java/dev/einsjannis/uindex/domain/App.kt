package dev.einsjannis.uindex.domain

import android.graphics.drawable.Drawable

data class App(
    val packageName: String,
    val label: String,
    val icon: Drawable?,
    val favoriteInfo: FavoriteInfo?,
    val isHidden: Boolean,
) {
    val isFavorite get() = favoriteInfo != null
}