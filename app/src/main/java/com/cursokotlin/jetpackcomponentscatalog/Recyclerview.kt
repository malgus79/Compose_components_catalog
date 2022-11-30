package com.cursokotlin.jetpackcomponentscatalog

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cursokotlin.jetpackcomponentscatalog.model.Superhero
import kotlinx.coroutines.launch

@Composable
fun SimpleRecyclerView() {
    /* LazyColumn = La lista de desplazamiento vertical que solo compone y presenta los elementos
    actualmente visibles. El bloque de contenido define un DSL que le permite emitir elementos de
    diferentes tipos. Por ejemplo, puede usar LazyListScope.item para agregar un solo elemento y
    LazyListScope.items para agregar una lista de elementos.
    */

    val myList = listOf("Aris", "Pepe", "Manolo", "Jaime")
    LazyColumn {
        item { Text(text = "Header") }
        items(myList) {
            Text(text = "Hola me llamo $it")
        }
        item { Text(text = "Footer") }
    }
}

@Composable
fun SuperHeroView() {
    val context = LocalContext.current  //para sacar el contexto que necesita el Toast (tiene que estar fuera de la fun)
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {  //spacedBy = un espacio entre medio
        items(getSuperheroes()) { superhero ->
            ItemHero(superhero = superhero)
            { Toast.makeText(context, it.realName, Toast.LENGTH_SHORT).show() } // lo que se haga aca sera cuando se clickea
        }
    }
}

@Composable
fun SuperHeroWithSpecialControlsView() {
    //crear estados para controlar el recyclerView
    val context = LocalContext.current
    val rvState = rememberLazyListState()  // controlar el estado del recyclerView
    val coroutinesScope = rememberCoroutineScope()

    Column {
        LazyColumn(
            state = rvState, verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)  //se agrego el weight para que se vea el boton
        ) {
            items(getSuperheroes()) { superhero ->
                ItemHero(superhero = superhero)
                { Toast.makeText(context, it.realName, Toast.LENGTH_SHORT).show() }
            }
        }

        val showbutton by remember {
            derivedStateOf {
                rvState.firstVisibleItemIndex > 0

                  /*
                  derivedStateOf = optimiza la recomposicion de la vista:
                  Crea un objeto State cuyo State.value es el resultado del cálculo. El resultado
                  del cálculo se almacenará en caché de tal manera que llamar a State.value
                  repetidamente no hará que el cálculo se ejecute varias veces, pero leer State.value
                  hará que todos los objetos de estado que se leyeron durante el cálculo se lean en
                  la instantánea actual. , lo que significa que esto se suscribirá correctamente a
                  los objetos de estado derivados si el valor se lee en un contexto observado, como
                  una función componible.

                  rvState.firstVisibleItemIndex > 0 = es decir, cuando se termine de visualizar el primet item aparecera el boton
                  */

                  /*
                  rvState.firstVisibleItemScrollOffset = El desplazamiento de desplazamiento del primer elemento visible. Desplazarse
                  hacia adelante es positivo, es decir, la cantidad que el elemento se compensa hacia atrás

                  firstVisibleItemScrollOffset = darnos un valor dependiendo del sitio en el que estemos
                  */
            }
        }

        rvState.firstVisibleItemScrollOffset

        if (showbutton) {

            Button(
                onClick = {
                    coroutinesScope.launch {
                        rvState.animateScrollToItem(0)  //al hacer click en boton -> nos lleva hacia arriba de todoo o a la posicion que se indique
                    }
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Soy un boton cool")
            }
        }
    }


}

@ExperimentalFoundationApi
@Composable
fun SuperHeroStickyView() {
    val context = LocalContext.current
    val superhero: Map<String, List<Superhero>> = getSuperheroes().groupBy { it.publisher }
    //para hacer un sticky necesitamos un map, en vez de un listado y PERO agrupado por un map

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        superhero.forEach { (publisher, mySuperHero) ->

            /* stickyHeader = Agrega un elemento de encabezado fijo, que permanecerá anclado incluso cuando se
            desplace después de él. El encabezado permanecerá anclado hasta que el próximo encabezado ocupe su lugar.
            */

            stickyHeader {
                Text(
                    text = publisher, modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Green), fontSize = 16.sp, color = Color.White
                )
            }

            items(mySuperHero) { superhero ->
                ItemHero(superhero = superhero)
                { Toast.makeText(context, it.realName, Toast.LENGTH_SHORT).show() }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperHeroGridView() {
    val context = LocalContext.current
    LazyVerticalGrid(cells = GridCells.Fixed(2), content = {
        items(getSuperheroes()) { superhero ->
            ItemHero(superhero = superhero)
            { Toast.makeText(context, it.realName, Toast.LENGTH_SHORT).show() }
        }
    }, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) // para definirle margenes por FUERA del grid
    /*
    cells permite 2 cosas
    -) permite poner un tamaño fijo de items:
            Fixed = Combina celdas con filas o columnas de números fijos.
            Por ejemplo, para la vertical LazyVerticalGrid Fixed(3) significaría que hay 3 columnas 1/3 del ancho principal.
    -) permite asignarle un tamaño minimo a un item:
            Adaptive = Combina celdas con un número adaptable de filas o columnas. Intentará colocar
            tantas filas o columnas como sea posible con la condición de que cada celda tenga al menos
            un espacio de tamaño mínimo y todoo el espacio adicional se distribuya de manera uniforme.
    */

}

@Composable
fun ItemHero(superhero: Superhero, onItemSelected: (Superhero) -> Unit) {
    //se encarga de pintar cada item
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            //.width(200.dp)
            .fillMaxWidth()
            .clickable { onItemSelected(superhero) }
            //.padding(horizontal = 16.dp, vertical = 8.dp)  //se puede aplicar padding a los items, margenes por DENTRO
    ) {
        Column {
            Image(
                painter = painterResource(id = superhero.photo),
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = superhero.superheroName,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = superhero.realName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp
            )
            Text(
                text = superhero.publisher,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            )
        }
    }
}

fun getSuperheroes(): List<Superhero> {
    //lista de datos
    return listOf(
        Superhero("Spiderman", "Petter Parker", "Marvel", R.drawable.spiderman),
        Superhero("Wolverine", "James Howlett", "Marvel", R.drawable.logan),
        Superhero("Batman", "Bruce Wayne", "DC", R.drawable.batman),
        Superhero("Thor", "Thor Odinson", "Marvel", R.drawable.thor),
        Superhero("Flash", "Jay Garrick", "DC", R.drawable.flash),
        Superhero("Green Lantern", "Alan Scott", "DC", R.drawable.green_lantern),
        Superhero("Wonder Woman", "Princess Diana", "DC", R.drawable.wonder_woman)
    )
}

/**
 Recomendacion de GOOGLE:
 -) no usar elementos que tengan un tamaño de 0 pixeles -> poner altos/anchos fijos
 -) evitar anidar componentes deslizables en la misma direccion
    un recyclerView vertical, que tenga un item con scroll tambien vertical -> todavia no anda bien esa funcionalidad
**/