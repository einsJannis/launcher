package dev.einsjannis.launcher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.einsjannis.launcher.menu.Menu
import dev.einsjannis.launcher.ui.theme.LauncherTheme
import kotlin.math.floor
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { LauncherTheme { Element() } }
    }
    @Composable
    fun Element() {
        Box(modifier = Modifier.fillMaxSize()) {
            val menu: MutableState<Menu?> = remember { mutableStateOf(null) }
            val offset: MutableState<Int> = remember { mutableStateOf(0) }
            val navHostController = rememberNavController()
            val application = LocalContext.current.applicationContext as Launcher
            NavHost(navHostController, application.favoriteScreen.name) {
                application.favoriteScreen.apply { composable(menu, offset) }
                application.listScreen.apply { composable(menu, offset) }
            }
            ScrollBar(Modifier.align(Alignment.CenterEnd), offset, navHostController)
            PopUp(menu)
        }
    }

    @Composable
    fun PopUp(menu: MutableState<Menu?>) {
        menu.value?.also {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000))
                    .clickable {
                        menu.value = null
                    }
            ) {
                BackHandler {
                    menu.value = null
                }
                it.Element(
                    Modifier.align(Alignment.BottomCenter).clickable(enabled = false) {  }
                )
            }
        }
    }

    @Composable
    fun ScrollBar(
        modifier: Modifier,
        offset: MutableState<Int>,
        navHostController: NavHostController
    ) {
        val categories = (applicationContext as Launcher).listScreen.categories
        fun offset(relative: Int): Float {
            return 1.2.pow(-(relative * relative).toDouble()).toFloat()*180f
        }
        val ctx = LocalContext.current
        val application = ctx.applicationContext as Launcher
        var dragActive by remember { mutableStateOf(false) }
        Column(
            modifier = modifier.pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        dragActive = true
                        val y = change.position.y
                        val currentDestination = navHostController.currentBackStackEntry?.destination?.route
                        Log.d("PointerInput", "PointerInputY $y")
                        Log.d("PointerInput", "PointerInputHeight ${size.height}")
                        val frac = (y / size.height.toFloat())
                        Log.d("PointerInput", "PointerInputFrac $frac")
                        val index = floor(categories.size * frac).toInt()
                        Log.d("PointerInput", "PointerInputIndex $index")
                        if (currentDestination != application.listScreen.name) {
                            Log.d("SWITCH", "SWITCH BC $currentDestination != ${application.listScreen.name}")
                            navHostController.navigate(application.listScreen.name)
                        }
                        if (offset.value != index)
                            offset.value = index

                    },
                    onDragEnd = {
                        dragActive = false
                    }
                )
            }
        ) {
            for ((index, category) in categories.mapIndexed { index, c -> Pair(index, c) }) {
                val animatedOffset by animateFloatAsState(
                    offset(relative = offset.value - index)
                )
                Text(
                    category.toString(), style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        shadow = Shadow(
                            color = Color.Black,
                            blurRadius = 7f
                        ),
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 5.dp
                    ).width(22.dp).offset {
                        if (dragActive)
                            IntOffset(-animatedOffset.toInt(), 0)
                        else IntOffset(0,0)
                    })
            }
        }
    }
}
