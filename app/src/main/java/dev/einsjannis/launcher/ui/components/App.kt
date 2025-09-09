package dev.einsjannis.launcher.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.einsjannis.launcher.domain.App

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App(app: App, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).combinedClickable(onClick = {
        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
        if (intent!=null) context.startActivity(intent)
    }, onLongClick = {
        popUpViewModel.open(app)
    })) {
        Icon(app)
        Title(app, Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun Icon(app: App) {
    val context = LocalContext.current
    val image = app.icon ?: context.resources.getDrawable(context.applicationInfo.icon, context.theme)
    val pixelSize = with(LocalDensity.current) {40.dp.roundToPx()}
    Image(image.toBitmap(width = pixelSize, height = pixelSize).asImageBitmap(), "icon", modifier = Modifier.padding(10.dp))
}

@Composable
fun Title(app: App, modifier: Modifier = Modifier) {
    Text(app.label, modifier = modifier.padding(10.dp))
}