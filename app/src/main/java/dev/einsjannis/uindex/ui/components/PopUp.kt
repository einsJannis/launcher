package dev.einsjannis.uindex.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dev.einsjannis.uindex.ui.screen.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUp(popUp: PopUpViewModel, favorites: FavoritesViewModel, modifier: Modifier = Modifier) {
    if (!popUp.isOpen.value) return
    val app = popUp.app.value!!
    ModalBottomSheet(onDismissRequest = {popUp.close()}) {
        BackHandler {
            popUp.close()
        }
        val context = LocalContext.current
        Column(modifier = modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)) {
                Icon(rememberDrawablePainter(app.icon))
                Title(app, Modifier.align(Alignment.CenterVertically))
            }
            HorizontalDivider()
            Button("Open Settings") {
                popUp.openSettings(context)
                popUp.close()
            }
            Button(if (app.isFavorite) "Unfavorite" else "Favorite") {
                popUp.toggleFavorite()
                popUp.close()
            }
            if (app.isFavorite) {
                Button("Move Up") {
                    favorites.moveUp(app)
                    popUp.close()
                }
                Button("Move Down") {
                    favorites.moveDown(app)
                    popUp.close()
                }
            }
            Button(if (app.isHidden) "Show" else "Hide") {
                popUp.toggleHidden()
                popUp.close()
            }
        }
    }
}

@Composable
fun Button(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)) {
        Text(text, modifier = Modifier.padding(20.dp))
    }
}
