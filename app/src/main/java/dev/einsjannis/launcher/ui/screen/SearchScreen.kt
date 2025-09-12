package dev.einsjannis.launcher.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.einsjannis.launcher.ui.components.App
import dev.einsjannis.launcher.ui.components.PopUpViewModel

@Composable
fun SearchScreen(popUp: PopUpViewModel, modifier: Modifier = Modifier, focusRequester: FocusRequester = remember { FocusRequester() }, searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)) {
    Column(modifier = modifier.fillMaxWidth().padding(top = 20.dp, start = 30.dp, end = 30.dp)) {
        val search = remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        LaunchedEffect(search.value) {
            searchViewModel.search(search.value)
        }
        TextField(
            value = search.value,
            onValueChange = { search.value = it },
            placeholder = { Text("Search") },
            leadingIcon = { /*TODO: Search icon*/ },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().clip(CircleShape).focusRequester(focusRequester)
        )
        val result by searchViewModel.result.collectAsState()
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
            itemsIndexed(result) { index, it ->
                if (index == 0) Box(modifier = Modifier.height(10.dp))
                App(it, popUp)
                if (index == result.lastIndex) Box(modifier = Modifier.height(10.dp))
            }
        }
    }
}