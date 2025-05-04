package dev.einsjannis.launcher.list

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
    fun Element(context: Context) {
        Element(context, Modifier.fillMaxWidth())
    }
    @Composable
    protected abstract fun Element(context: Context, modifier: Modifier)
}