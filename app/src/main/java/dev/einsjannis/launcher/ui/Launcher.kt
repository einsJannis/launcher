package dev.einsjannis.launcher.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.einsjannis.launcher.ui.components.Button
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
    controller: NavHostController = rememberNavController(),
    scrollBar: MutableScrollBarViewModel = viewModel(factory = MutableScrollBarViewModel.Factory(list, controller)),
    popUp: PopUpViewModel = viewModel(factory = PopUpViewModel.Factory)
) {
    Scaffold(modifier = modifier.fillMaxSize(), containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onBackground) {
        val padding = it
        NavHost(controller, Screen.FAVORITE.toString()) {
            composable(Screen.FAVORITE.toString()) {
                Box(modifier = Modifier.padding(padding)) { //CONTENT
                    FavoritesScreen(favorites, popUp, modifier)
                    ScrollBar(scrollBar, modifier = Modifier.align(Alignment.CenterEnd))
                    SearchButton(controller, modifier = Modifier.align(Alignment.BottomEnd))
                }
            }
            composable(Screen.LIST.toString()) {
                Box(modifier = Modifier.padding(padding)) { //CONTENT
                    ListScreen(list, scrollBar, popUp, modifier)
                    ScrollBar(scrollBar, modifier = Modifier.align(Alignment.CenterEnd))
                    SearchButton(controller, modifier = Modifier.align(Alignment.BottomEnd))
                }
            }
            composable(Screen.SEARCH.toString()) {
                Box(modifier = Modifier.padding(padding)) { //CONTENT
                    SearchScreen(popUp)
                }
            }
        }
        PopUp(popUp,favorites)
    }
}

@Composable
fun SearchButton(navHostController: NavHostController, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(20.dp)) {
        Button("Search", modifier = Modifier.clip(CircleShape).background(Color.DarkGray).size(80.dp)) {
            navHostController.navigate(Screen.SEARCH.toString())
        }
    }
}