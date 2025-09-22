package dev.einsjannis.launcher.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorizedAnimationSpec
import androidx.compose.animation.core.VectorizedFloatAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScrollBar(scrollBar: MutableScrollBarViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.pointerInput(Unit, scrollBar.handlePointerInput).width(60.dp).padding(start = 27.dp, end = 7.dp)
    ) {
        val categoryIndices by scrollBar.list.categoryIndices.collectAsState()
        val items by remember { derivedStateOf { categoryIndices.map { scrollBar.list.allCategories[it] } } }
        items.iterator().withIndex().forEach { (index, category) ->
            val animateFloat by animateFloatAsState(scrollBar.offset(index))
            val modifier = if (scrollBar.isHeld.value) { Modifier.offset(x = (-animateFloat).dp) } else { Modifier }
            Text(category.title, modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(5.dp)).clickable(onClick = scrollBar.handleClick(index)), textAlign = TextAlign.Center)
        }
    }
}