package dev.einsjannis.launcher.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.ui.components.App
import dev.einsjannis.launcher.ui.components.Calendar
import dev.einsjannis.launcher.ui.components.PopUpViewModel
import dev.einsjannis.launcher.ui.components.Watch

@Composable
fun FavoritesScreen(favorites: FavoritesViewModel, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    val apps by favorites.apps.collectAsState()
    LazyColumn(modifier.fillMaxSize().padding(horizontal = 40.dp)) {
        itemsIndexed(apps) { index, app ->
            if (index == 0) {
                val height = (LocalConfiguration.current.screenHeightDp.dp / 2
                  - 200.dp - with(LocalDensity.current) { 60.sp.toDp() })
                Box(Modifier.height(height).fillMaxWidth())
                Watch(Modifier.fillMaxWidth())
                Calendar(Modifier.fillMaxWidth())
            }
            App(app,popUpViewModel)
        }
    }
}