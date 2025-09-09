package dev.einsjannis.launcher.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.ui.components.App
import dev.einsjannis.launcher.ui.components.PopUpViewModel

@Composable
fun FavoritesScreen(favorites: FavoritesViewModel, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(horizontal = 40.dp)) {
        val height = LocalConfiguration.current.screenHeightDp.dp/2 - 100.dp
        Box(Modifier.height(height).fillMaxWidth())
        val apps by favorites.apps.collectAsState()
        for (app in apps) {
            App(app,popUpViewModel)
        }
    }
}