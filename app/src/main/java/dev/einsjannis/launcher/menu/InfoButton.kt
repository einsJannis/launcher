package dev.einsjannis.launcher.menu

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class InfoButton : Button() {
    @Composable
    override fun Element(context: Context, popUp: Menu, modifier: Modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.clickable {
            context.startActivity(popUp.appItem.app.infoIntent)
        }) {
            Text("Info", color = Color.White, modifier = Modifier.padding(20.dp))
        }
    }
}