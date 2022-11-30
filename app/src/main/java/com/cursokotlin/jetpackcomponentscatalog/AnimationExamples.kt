package com.cursokotlin.jetpackcomponentscatalog

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorAnimationSimple() {
    var firstColor by rememberSaveable {
        mutableStateOf(false)
    }
    var showBox by rememberSaveable {
        mutableStateOf(true)
    }
    val realColor by animateColorAsState(
        targetValue = if (firstColor) Color.Red else Color.Yellow,
        animationSpec = tween(durationMillis = 1000),
        finishedListener = { showBox = false }  //esto llama cuando la animacion finalice
    )

    if (showBox) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(realColor)
            .clickable { firstColor = !firstColor })
    }

}

@Composable
fun SizeAnimation() {
    var smallSize by rememberSaveable { mutableStateOf(true) }
    val size by animateDpAsState(targetValue = if (smallSize) 50.dp else 100.dp,
        animationSpec = tween(durationMillis = 500),
        finishedListener = {if(!smallSize){} })
    Box(modifier = Modifier
        .size(size)
        .background(Color.Cyan)
        .clickable { smallSize = !smallSize })
}

/*
setContent {
            JetpackComponentsCatalogTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    SizeAnimation()
                }
            }
        }
 */