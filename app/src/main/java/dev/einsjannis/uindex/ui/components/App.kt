package dev.einsjannis.uindex.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dev.einsjannis.uindex.domain.App

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App(app: App, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Element(
        app.label,
        rememberDrawablePainter(app.icon ?: context.getDrawable(context.applicationInfo.icon)),
        onClick = {
            val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
            if (intent!=null) context.startActivity(intent)
        },
        onLongClick = {
            popUpViewModel.open(app)
        }
    )
}

@Composable
fun Title(app: App, modifier: Modifier = Modifier) {
    Title(app.label, modifier)
}