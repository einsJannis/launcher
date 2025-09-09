package dev.einsjannis.launcher.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun Launcher(
    modifier: Modifier = Modifier,
    favorites: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
    list: ListViewModel = viewModel(factory = ListViewModel.Factory),
    controller: NavHostController = rememberNavController(),
    scrollBar: MutableScrollBarViewModel = viewModel(factory = MutableScrollBarViewModel.Factory(list, controller)),
    popUp: PopUpViewModel = viewModel(factory = PopUpViewModel.Factory)
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(start = 5.dp, end = 5.dp)) { //CONTENT
            NavHost(controller, Screen.FAVORITE.toString()) {
                composable(Screen.FAVORITE.toString()) {
                    FavoritesScreen(favorites, popUp, modifier)
                }
                composable(Screen.LIST.toString()) {
                    ListScreen(list, scrollBar, popUp, modifier)
                }
            }
            ScrollBar(scrollBar, modifier = Modifier.align(Alignment.CenterEnd))
        }
        PopUp(popUp)
    }
}