package dev.einsjannis.launcher.list

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Items(val items: List<Item>) {
    @Composable
    fun Element(context: Context) {
        LazyColumn (modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            items(items) { item ->
                item.Element(context)
            }
        }
    }
}