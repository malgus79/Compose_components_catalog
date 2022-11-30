package com.cursokotlin.jetpackcomponentscatalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cursokotlin.jetpackcomponentscatalog.model.Routes
import com.cursokotlin.jetpackcomponentscatalog.model.Routes.Pantalla2
import com.cursokotlin.jetpackcomponentscatalog.model.Routes.Pantalla3

/*
        setContent {
            JetpackComponentsCatalogTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    //para controlar los estados de la navegacion
                    val navigationControler = rememberNavController()
                    NavHost(navController = navigationControler,
                        startDestination = Routes.Pantalla1.route) {
                        //startDestination = donde empieza la actividad
                        //para navegar entre pantallas -> asignarles un ID unica
                        composable(Routes.Pantalla1.route) { Screen1(navigationControler) }
                        composable(Routes.Pantalla2.route) { Screen2(navigationControler) }
                        composable(Routes.Pantalla3.route) { Screen3(navigationControler) }
                        //para pasar un parametro: valor / {parametro}
                        //backStackEntry: ahi dentro estara el argumento {parametro}
                        //por defecto el parametro es un STRING
                        //para que se ade otro tipo -> poner arguments despues de la ruta
                        composable(Routes.Pantalla4.route,
                            arguments = listOf(navArgument("age") { type = NavType.IntType })
                        ) { backStackEntry ->
                            Screen4(
                                navigationControler,
                                backStackEntry.arguments?.getInt("age") ?: 0
                            )
                        }
                        composable(Routes.Pantalla5.route,
                            arguments = listOf(navArgument("name", { defaultValue = "Pepe" })))
                        { backStackEntry ->
                            Screen5(
                                navigationControler,
                                backStackEntry.arguments?.getString("name")
                            )
                        }
                    }
                }
            }
        }
*/

@ExperimentalFoundationApi
@Composable
fun NavigationComplete() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.Pantalla1.route
    ) {
        composable(Routes.Pantalla1.route) { Screen1(navigationController) }
        composable(Pantalla2.route) { Screen2(navigationController) }
        composable(Pantalla3.route) { Screen3(navigationController) }
        composable(
            Routes.Pantalla4.route,
            arguments = listOf(navArgument("age") { type = NavType.IntType })
        ) { backStackEntry ->
            Screen4(
                navigationController,
                backStackEntry.arguments?.getInt("age") ?: 0
            )
        }
        composable(
            Routes.Pantalla5.route,
            arguments = listOf(navArgument("name") { defaultValue = "Pepe" })
        )
        { backStackEntry ->
            Screen5(
                navigationController,
                backStackEntry.arguments?.getString("name")
            )

        }
    }
}

@Composable
fun Screen1(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Text(
            text = "Pantalla 1",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { navController.navigate(Pantalla2.route) })
    }
}

@Composable
fun Screen2(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text(text = "Pantalla 2", modifier = Modifier
            .align(Alignment.Center)
            .clickable {
                navController.navigate(
                    Pantalla3.route
                )
            })
    }
}

@Composable
fun Screen3(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        Text(
            text = "Pantalla 3",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { navController.navigate(Routes.Pantalla4.createRoute(29)) })
    }
}

/*
Screen 1, 2 y 3 -> navegacion comun
*/
/*
Screen4 y 5 -> navegacion con argumentos
*/

@Composable
fun Screen4(navController: NavHostController, age: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(text = "Tengo $age años", modifier = Modifier
            .align(Alignment.Center)
            .clickable {
                navController.navigate(
                    "pantalla5")
            })
    }

    /*
    para pasar parametros se puede usar 2 opciones: Obligatorio u Opcional
    */
}

@Composable
fun Screen5(navController: NavHostController, name: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Text(text = "Me llamo $name", modifier = Modifier.align(Alignment.Center))
    }
}