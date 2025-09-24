package dev.einsjannis.uindex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Category(categoryViewModel: CategoryViewModel, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(categoryViewModel.title, Modifier.padding(vertical = 5.dp))
        val apps by categoryViewModel.apps.collectAsState()
        for (app in apps) {
            App(app, popUpViewModel)
        }
    }
}