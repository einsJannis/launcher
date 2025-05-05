package dev.einsjannis.launcher.list

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class SpaceItem(private val height: Dp = 250.dp): Item() {
    @Composable
    override fun Element(context: Context, modifier: Modifier) {
        Box(modifier = modifier.height(height))
    }
}