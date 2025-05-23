package dev.einsjannis.launcher.menu

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

abstract class Button {
    @Composable
    abstract fun Element(popUp: Menu, modifier: Modifier = Modifier)
}