package dev.einsjannis.launcher.list

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.einsjannis.launcher.AppInfo
import dev.einsjannis.launcher.menu.InfoButton
import dev.einsjannis.launcher.menu.Menu

class AppItem(val app: AppInfo): Item() {
    val popUp = Menu(this, buttons = listOf(InfoButton()))
    @Composable
    fun Icon(context: Context, modifier: Modifier = Modifier) {
        val imageBitmap = app.icon.toBitmap().asImageBitmap()
        Image(imageBitmap, app.name + " Icon", modifier)
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Element(context: Context, modifier: Modifier, popUp: MutableState<Menu?>) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.height(80.dp).padding(10.dp).combinedClickable(
                onClick = { context.startActivity(app.launchIntent) },
                onLongClick = { popUp.value = this.popUp }
            )
        ) {
            Icon(context)
            Title(context, app.name, Modifier.padding(start = 20.dp))
        }
    }

}