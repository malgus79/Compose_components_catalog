package com.cursokotlin.jetpackcomponentscatalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/*
Scaffold = se considera otro contenedor (el esqueleto de una pantalla), esta creado para montar vistasm mas sencillas

Material Design layout.
Scaffold implementa la estructura de diseño visual de diseño de material básico.
Este componente proporciona una API para reunir varios componentes materiales para construir su pantalla,
asegurando una estrategia de diseño adecuada para ellos y recopilando los datos necesarios para que
estos componentes funcionen juntos correctamente.

Para componentes similares que implementan diferentes estructuras de diseño, consulte BackdropScaffold,
que usa un fondo como pieza central de la pantalla, y
BottomSheetScaffold, que usa una hoja inferior persistente como pieza central de la pantalla.

el Scaffold necesita guardar su estado, lo necesita para algunos componentes:
snackbar
*/

@Composable
fun ScaffoldExample() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MyTopAppBar(onClickIcon = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "Has pulsado $it"
                    )
                }
            }, onClickDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } })  //lambda para ABRIR el drawer
        },
        scaffoldState = scaffoldState,
        bottomBar = { MyBottomNavigation() },
        floatingActionButton = { MyFAB() },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,  //ignorar la button bar
        drawerContent = { MyDrawer { coroutineScope.launch { scaffoldState.drawerState.close() } } },  //lambda para CERRAR el drawer
        drawerGesturesEnabled = false  //para desplazarlo al centro, por defecto es true
    ) {

    }
}

@Composable
fun MyTopAppBar(onClickIcon: (String) -> Unit, onClickDrawer: () -> Unit) {
    /*TopAppBar = toolbar
    La barra superior de la aplicación muestra información y acciones relacionadas con la pantalla actual.
    Esta TopAppBar tiene ranuras para un título, un ícono de navegación y acciones. Tenga en cuenta
    que la ranura del título se inserta desde el principio de acuerdo con las especificaciones:
    para casos de uso personalizados, como centrar el título horizontalmente, use la otra sobrecarga
    de TopAppBar para una TopAppBar genérica sin restricción de contenido.
    */
    TopAppBar(
        title = { Text(text = "Mi primera toolbar") },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        elevation = 4.dp,  //poner elevacion para el tema de las sombras
        /*
        esta ejemplo es para hacer la flecha para volver:
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
        */
        navigationIcon = {  //esto pone la navegacion a la izq del titulo de la TopAppBar
            IconButton(onClick = { onClickDrawer() }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu")
            }
        },
        /*
        actions = botones al final (derecha) de la TopAppBar) para realizar acciones
        Las acciones que se muestran al final de TopAppBar. Esto normalmente debería ser IconButtons.
        El diseño predeterminado aquí es una fila, por lo que los iconos del interior se colocarán horizontalmente.
        */
        actions = {
            IconButton(onClick = { onClickIcon("Buscar") }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
            }
        //se pueden colocar mas de 1 IconButton
            IconButton(onClick = { onClickIcon("Peligro!") }) {
                Icon(imageVector = Icons.Filled.Dangerous, contentDescription = "dangerous")
            }
        }
    )
}

@Composable
fun MyBottomNavigation() {
    var index by remember { mutableStateOf(0) }
    //Al bottonNav cada item debe ser un componente exacto no puede ser cualquiera -> BottomNavigationItem
    BottomNavigation(backgroundColor = Color.Red, contentColor = Color.White) {
        BottomNavigationItem(selected = index == 0, onClick = { index = 0 }, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home"
            )
        }, label = { Text(text = "Home") })
        BottomNavigationItem(selected = index == 1, onClick = { index = 1 }, icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "fav"
            )
        }, label = { Text(text = "FAV") })
        BottomNavigationItem(selected = index == 2, onClick = { index = 2 }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "person"
            )
        }, label = { Text(text = "Person") })
    }
}

@Composable
fun MyFAB() {
    FloatingActionButton(
        onClick = { },
        backgroundColor = Color.Yellow,
        contentColor = Color.Black
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

@Composable
fun MyDrawer(onCloseDrawer: () -> Unit) {
    Column(Modifier.padding(8.dp)) {
        Text(
            text = "Primera opcion", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Segunda opcion", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Tercera opcion", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Cuarta opcion", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
    }
}