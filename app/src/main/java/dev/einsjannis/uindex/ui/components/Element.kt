package dev.einsjannis.uindex.ui.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun Info(text: String) {
    Element(
        text,
        rememberVectorPainter(Icons.Outlined.Info),
        tint = Color.White
    )
}

@Composable
fun Element(title: String, painter: Painter, onClick: (() -> Unit)? = null, onLongClick: () -> Unit = {}, tint: Color = Color.Unspecified) {
    var modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
    onClick?.let { modifier = modifier.combinedClickable(onClick = it, onLongClick = onLongClick) }
    Row (modifier = modifier) {
        Icon(painter, tint)
        Title(title, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun Icon(painter: Painter, tint: Color = Color.Unspecified) {
    Icon(painter, "icon", Modifier.padding(10.dp).size(40.dp), tint = tint)
}

@Composable
fun Title(title: String, modifier: Modifier = Modifier) {
    Text(title, modifier = modifier.padding(10.dp))
}