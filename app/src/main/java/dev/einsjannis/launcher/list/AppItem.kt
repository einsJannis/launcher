package dev.einsjannis.launcher.list

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.einsjannis.launcher.AppInfo

class AppItem(val app: AppInfo): Item() {
    @Composable
    fun Icon(context: Context) {
        val imageBitmap = app.icon.toBitmap().asImageBitmap()
        Image(imageBitmap, app.name + " Icon")
    }
    @Composable
    override fun Element(context: Context, modifier: Modifier) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.height(80.dp).padding(10.dp).clickable {
                context.startActivity(app.launchIntent)
            }
        ) {
            Icon(context)
            Title(context, app.name, Modifier.padding(start = 20.dp))
        }
    }

}