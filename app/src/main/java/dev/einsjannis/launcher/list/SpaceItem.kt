package dev.einsjannis.launcher.list

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.menu.Menu

class SpaceItem(private val height: Dp = 250.dp): Item() {
    @Composable
    override fun Element(modifier: Modifier, menu: MutableState<Menu?>) {
        Box(modifier = modifier.height(height))
    }
}