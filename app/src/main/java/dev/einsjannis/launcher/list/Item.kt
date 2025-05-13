package dev.einsjannis.launcher.list

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.menu.Menu


abstract class Item {
    @Composable
    fun Title(
        context: Context,
        text: String,
        modifier: Modifier = Modifier,
        style: TextStyle? = null
    ) {
        Text(text, style = TextStyle(
            color = Color.White, // TODO: Base on inversion of dominant wallpaper color
            fontSize = 22.sp,
            shadow = Shadow(blurRadius = 7f)
        ).merge(style), modifier = modifier)
    }
    @Composable
    fun Element(context: Context, popUp: MutableState<Menu?>) {
        Element(context, Modifier.fillMaxWidth(), popUp)
    }
    @Composable
    abstract fun Element(context: Context, modifier: Modifier, popUp: MutableState<Menu?>)
}