package com.cursokotlin.jetpackcomponentscatalog

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun BasicSlider() {
    /* Slider = Los controles deslizantes permiten a los usuarios realizar selecciones a partir de un rango de valores.
    Los controles deslizantes reflejan un rango de valores a lo largo de una barra, de la cual los usuarios
    pueden seleccionar un solo valor. Son ideales para ajustar configuraciones como el volumen, el brillo o aplicar filtros de imagen.
    */

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var sliderPosition by remember { mutableStateOf(0f) }
        Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
        //it = valor del slider cuando se vaya moviendo
        Text(text = sliderPosition.toString())
    }
}

@Composable
fun AdvanceSlider() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var sliderPosition by remember { mutableStateOf(0f) }
        var completeValue by remember { mutableStateOf("") }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            onValueChangeFinished = { completeValue = sliderPosition.toString() },  //se llama cuando el onValueChange pare
            valueRange = 0f..10f,  //rango de valores
            steps = 9,  //puntos en el slider = rango total - 2 posiciones (el 0 y el 10)
            enabled = false  //ejemplo: con un state se podria habilitar
        )
        Text(text = completeValue)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyRangeSlider() {
    /* RangeSlider = Los controles deslizantes de rango amplían el control deslizante utilizando
    los mismos conceptos, pero permiten al usuario seleccionar 2 valores.
    Los dos valores aún están limitados por el rango de valores, pero tampoco pueden cruzarse entre sí.
    Use controles deslizantes de rango continuos para permitir a los usuarios realizar selecciones
    significativas que no requieren valores específicos
    */
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        var currentRange by remember { mutableStateOf(0f..10f) }  //poner el rango en el state

        RangeSlider(
            values = currentRange,
            onValueChange = { currentRange = it },
            valueRange = 0f..10f,
            steps = 9
        )

        Text(text = "Valor inferior ${currentRange.start}")
        Text(text = "Valor Superior ${currentRange.endInclusive}")
    }
}
