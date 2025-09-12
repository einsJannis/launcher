package dev.einsjannis.launcher.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.ui.screen.FavoritesViewModel

@Composable
fun PopUp(popUp: PopUpViewModel, favorites: FavoritesViewModel, modifier: Modifier = Modifier) {
    if (!popUp.isOpen.value) return
    val app = popUp.app.value!!
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x88000000))
            .clickable {
                popUp.close()
            }
    ) {
        BackHandler {
            popUp.close()
        }
        val context = LocalContext.current
        val modifier = Modifier.align(Alignment.BottomCenter).clickable(enabled = false) {  }
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            tonalElevation = 8.dp
        ) {
            Column(modifier = modifier.fillMaxWidth().background(Color.DarkGray)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
                    Icon(app)
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
}

@Composable
fun Button(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            //.padding(20.dp)
            .clickable(onClick = onClick)) {
        Text(text, modifier = Modifier.padding(20.dp))
    }
}
