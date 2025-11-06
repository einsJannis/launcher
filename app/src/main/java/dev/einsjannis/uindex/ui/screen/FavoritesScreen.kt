package dev.einsjannis.uindex.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.uindex.ui.components.App
import dev.einsjannis.uindex.ui.components.Calendar
import dev.einsjannis.uindex.ui.components.Element
import dev.einsjannis.uindex.ui.components.Info
import dev.einsjannis.uindex.ui.components.PopUpViewModel
import dev.einsjannis.uindex.ui.components.Watch

@Composable
fun FavoritesScreen(favorites: FavoritesViewModel, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    val apps by favorites.apps.collectAsState()
    LazyColumn(modifier.fillMaxSize().padding(horizontal = 40.dp)) {
        if (apps.isEmpty()) items(listOf(Unit)) {
            val height = (LocalConfiguration.current.screenHeightDp.dp / 2
                    - 200.dp - with(LocalDensity.current) { 60.sp.toDp() })
            Box(Modifier.height(height).fillMaxWidth())
            Watch(Modifier.fillMaxWidth())
            Calendar(Modifier.fillMaxWidth())
            Info("Swipe on edge to access apps")
        } else itemsIndexed(apps) { index, app ->
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