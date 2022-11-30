package com.cursokotlin.jetpackcomponentscatalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
        finishedListener = {
            if (!smallSize) {
            }
        })
    Box(modifier = Modifier
        .size(size)
        .background(Color.Cyan)
        .clickable { smallSize = !smallSize })
}

//hacer una columna con un boton y un box. Cada vez que se pulse en el btn se muestra/oculta le box
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VisibilityAnimation() {

    var isVisible by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = "mostrar/Ocultar")
        }
        Spacer(modifier = Modifier.size(50.dp))

        AnimatedVisibility (isVisible) {
            Box(
                Modifier
                    .size(150.dp)
                    .background(Color.Red)
            )
        }
    }
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