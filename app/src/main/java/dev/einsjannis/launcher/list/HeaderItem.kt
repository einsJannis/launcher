package dev.einsjannis.launcher.list

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class HeaderItem(val text: String): Item() {
    @Composable
    override fun Element(context: Context, modifier: Modifier) {
        Box(modifier = modifier.padding(horizontal = 10.dp).padding(top = 20.dp, bottom = 10.dp)) {
            Title(context, text)
        }
    }
}