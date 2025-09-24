package dev.einsjannis.uindex.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.einsjannis.uindex.ui.components.Category
import dev.einsjannis.uindex.ui.components.PopUpViewModel
import dev.einsjannis.uindex.ui.components.ScrollBarViewModel

@Composable
fun ListScreen(list: ListViewModel, scrollBar: ScrollBarViewModel, popUpViewModel: PopUpViewModel, modifier: Modifier = Modifier) {
    val categoryIndices = list.categoryIndices.collectAsState()
    val categories by remember { derivedStateOf { categoryIndices.value.map { list.allCategories[it] } } }
    val lazyListState = rememberLazyListState()
    val height = LocalConfiguration.current.screenHeightDp.dp/2 - 200.dp
    val heightPx = with(LocalDensity.current) { height.roundToPx() }
    LaunchedEffect(scrollBar.index.value) {
        lazyListState.animateScrollToItem(scrollBar.index.value, scrollOffset = -heightPx)
    }
    LazyColumn(modifier = modifier.fillMaxSize().padding(horizontal = 40.dp), state = lazyListState) {
        itemsIndexed(categories) { index, it ->
            if (index == 0) Box(Modifier.height(height).fillMaxWidth())
            Category(it, popUpViewModel)
            if (index == categories.lastIndex) Box(Modifier.height(height).fillMaxWidth())
        }
    }
}
