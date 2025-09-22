package dev.einsjannis.launcher.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.einsjannis.launcher.ui.components.MutableScrollBarViewModel
import dev.einsjannis.launcher.ui.components.PopUp
import dev.einsjannis.launcher.ui.components.PopUpViewModel
import dev.einsjannis.launcher.ui.components.ScrollBar
import dev.einsjannis.launcher.ui.screen.FavoritesScreen
import dev.einsjannis.launcher.ui.screen.FavoritesViewModel
import dev.einsjannis.launcher.ui.screen.ListScreen
import dev.einsjannis.launcher.ui.screen.ListViewModel
import dev.einsjannis.launcher.ui.screen.Screen
import dev.einsjannis.launcher.ui.screen.SearchScreen

@Composable
fun Launcher(
    modifier: Modifier = Modifier,
    favorites: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
    list: ListViewModel = viewModel(factory = ListViewModel.Factory),
    screen: MutableState<Screen> = remember { mutableStateOf(Screen.FAVORITE) },
    scrollBar: MutableScrollBarViewModel = viewModel(factory = MutableScrollBarViewModel.Factory(list, screen)),
    popUp: PopUpViewModel = viewModel(factory = PopUpViewModel.Factory)
) {
    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onBackground, floatingActionButton = {
        if (screen.value != Screen.SEARCH)
            SearchButton(screen)
    }) {
        val padding = it
        LifecycleResumeEffect(Unit) {
            screen.value = Screen.FAVORITE
            onPauseOrDispose {}
        }
        BackHandler {
            screen.value = Screen.FAVORITE
        }
        Box(modifier = Modifier.padding(padding)) { //CONTENT
            when (screen.value) {
                Screen.FAVORITE -> {
                    FavoritesScreen(favorites, popUp, modifier)
                }
                Screen.LIST -> {
                    ListScreen(list, scrollBar, popUp, modifier)
                }
                Screen.SEARCH -> {
                    SearchScreen(popUp)
                }
            }
            if (screen.value != Screen.SEARCH)
                ScrollBar(scrollBar, modifier = Modifier.align(Alignment.CenterEnd))
        }
        PopUp(popUp,favorites)
    }
}

@Composable
fun SearchButton(screen: MutableState<Screen>, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick = { screen.value = Screen.SEARCH }, modifier = modifier.clip(RoundedCornerShape(35.dp)), containerColor = Color.Black) {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White, modifier = Modifier.size(70.dp).padding(20.dp))
    }
}