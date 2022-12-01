package com.cursokotlin.jetpackcomponentscatalog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cursokotlin.jetpackcomponentscatalog.model.Routes
import com.cursokotlin.jetpackcomponentscatalog.ui.CheckInfo
import com.cursokotlin.jetpackcomponentscatalog.ui.theme.JetpackComponentsCatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComponentsCatalogTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    CrossfadeExampleAnimation()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComponentsCatalogTheme {
        SuperHeroView()
    }
}

@Composable
fun MyDropDownMenu() {

    /* DropdownMenu = Un menú desplegable es una forma compacta de mostrar múltiples opciones.
    Aparece al interactuar con un elemento (como un icono o botón) o cuando los usuarios
    realizan una acción específica.
    */

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val desserts = listOf("Helado", "Chocolate", "Café", "Fruta", "Natillas", "Chilaquiles")
    //var selectedText by remember { mutableStateOf(desserts[0]) }  //esto es para que aparezca el primer item por defecto

    Column(Modifier.padding(0.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,  //solo leer, no se puede escribir en él
            modifier = Modifier  //hacerlo clickable
                .clickable {
                    expanded = true
                }  //ponerlo a true -> cuando se cliclea se abre el menu
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,  //boolean: si esta expandido muestra todoo, si no -> no muestra ninguno
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            //aqui se rellena el menu
            desserts.forEach { dessert ->
                DropdownMenuItem(onClick = {  //un item x cada uno de los postres que se vera en el listado
                    expanded = false  //al cliclear en un item -> cerrar el menu
                    selectedText = dessert
                }) {
                    Text(text = dessert)
                }
            }
        }
    }

    /*
    MyDropDownMenu()  //otra opcion es sacarlo de la Column y eliminar el fillMaxSize del Surface
    */

}

@Composable
fun MyRadioButton() {
    //RadioButton = Los botones de radio permiten a los usuarios seleccionar una opción de un conjunto.
    //una de las multiples opciones de una unica seleccion. Ejemplo: 5 opciones y una marcada, al marcar otra nueva opcion, se desmarca la seleccionada

    Row(Modifier.fillMaxWidth()) {

        RadioButton(
            selected = false, onClick = { }, enabled = false, colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,  //seleccionado
                unselectedColor = Color.Yellow,  //deseleccionado
                disabledColor = Color.Green  //desabilitado
            )
        )

        Text(text = "Ejemplo 1", modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun MyRadioButtonList(name: String, onItemSelected: (String) -> Unit) {
    //Radio Button solo puede estar marcado 1 solo de todos

    //state hoisting con multiples radioButton
    Column(Modifier.fillMaxWidth()) {
        Row() {
            RadioButton(selected = name == "Aris", onClick = { onItemSelected("Aris") })
            Text(text = "Aris")
        }
        Row() {
            RadioButton(selected = name == "David", onClick = { onItemSelected("David") })
            Text(text = "David")
        }
        Row() {
            RadioButton(selected = name == "Fulanito", onClick = { onItemSelected("Fulanito") })
            Text(text = "Fulanito")
        }
        Row() {
            RadioButton(selected = name == "Pepe", onClick = { onItemSelected("Pepe") })
            Text(text = "Pepe")
        }
    }
    /*
    setContent {
        JetpackComponentsCatalogTheme {

            se uso esto para el radiobuttonlist
             var selected by remember {
                 mutableStateOf("Aris")
             }
             */

}

@Composable
fun MyTriStatusCheckBox() {
    //CheckBox con 3 estados: enable, disable e inderterminado

    var status by rememberSaveable { mutableStateOf(ToggleableState.Off) }
    TriStateCheckbox(state = status, onClick = {
        status = when (status) {
            ToggleableState.On -> {
                ToggleableState.Off
            }
            ToggleableState.Off -> ToggleableState.Indeterminate
            ToggleableState.Indeterminate -> ToggleableState.On
        }
    })
    /*
    TriStateCheckbox = Las casillas de verificación pueden tener una relación padre-hijo con otras casillas de verificación.
    Cuando la casilla principal está marcada, todas las casillas secundarias están marcadas.
    Si una casilla de verificación principal no está marcada, todas las casillas de verificación secundarias están desmarcadas.
    Si algunas, pero no todas, las casillas de verificación secundarias están marcadas, la casilla de verificación principal se convierte en una casilla de verificación indeterminada.
     */


    /*                {
                    val myOptions = getOptions(listOf("aris", "ejemplo", "pikachu"))
                    Column() {
                        MyTriStatusCheckBox()  //opcion PADRE
                        myOptions.forEach {
                            //cada uno de sus HIJOS
                            MyCheckBoxWithTextCompleted(it)
                        }
                    }
               }
*/

}

@Composable
fun getOptions(titles: List<String>): List<CheckInfo> {
    //State Hoisting con multiples checkBox
    //le pasamos un listado de titulos y devuelve un listado de ckeckInfo (para saber cual es el check seleccionado)

    //.map = recorrerlo y devolverlo
    return titles.map {
        //crear por cada titulo un objeto checkInfo
        var status by rememberSaveable { mutableStateOf(false) }
        CheckInfo(
            title = it,
            selected = status,
            onCheckedChange = { myNewStatus -> status = myNewStatus }
        )
    }
}

@Composable
fun MyCheckBoxWithTextCompleted(checkInfo: CheckInfo) {
    //el state se elimina de aqui porque se gestiona en el onCreate
    //var state by rememberSaveable { mutableStateOf(false) }

    //esta funcion va a recibir como parametro un objeto de tipo CheckBox
    Row(Modifier.padding(8.dp)) {
        Checkbox(
            checked = checkInfo.selected,
            onCheckedChange = { checkInfo.onCheckedChange(!checkInfo.selected) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = checkInfo.title)
    }
}

@Composable
fun MyCheckBoxWithText() {
    //texto junto al ckeckBox
    var state by rememberSaveable { mutableStateOf(false) }

    Row(Modifier.padding(8.dp)) {
        Checkbox(checked = state, onCheckedChange = { state = !state })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Ejemplo 1")
    }
    //esto es para hacer un solo ckeckBox, para hacer una lista se debe usar mutableState
    //NO SE PUEDE pasar un objeto la mutableState (xxx)
}

@Composable
fun MyCheckBox() {
    //Checkbox = Las casillas de verificación permiten a los usuarios seleccionar uno o más elementos de un conjunto. Las casillas de verificación pueden activar o desactivar una opción.
    var state by rememberSaveable { mutableStateOf(false) }

    Checkbox(
        checked = state,
        onCheckedChange = { state = !state },
        enabled = true,
        colors = CheckboxDefaults.colors(
            checkedColor = Color.Red,  //fondo del chequeado
            uncheckedColor = Color.Yellow,  //fondo del no-chequeado
            checkmarkColor = Color.Blue  //tilde del chequeado
        )
    )
}

@Composable
fun MySwitch() {
    // en  Switch, SIEMPRE se usa estados
    //Cambia el estado de un solo elemento entre activado o desactivado.
    var state by rememberSaveable { mutableStateOf(false) }

    Switch(
        checked = state,
        onCheckedChange = { state = !state },  //cuando se pulsa -> se desactiva
        enabled = false,  //false -> no se permite cambiar el estado
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = Color.Red,
            uncheckedTrackColor = Color.Magenta,
            checkedThumbColor = Color.Green,
            checkedTrackColor = Color.Cyan,
            checkedTrackAlpha = 0.1f,
            uncheckedTrackAlpha = 0.1f,
            disabledCheckedTrackColor = Color.Yellow,
            disabledCheckedThumbColor = Color.Yellow,
            disabledUncheckedThumbColor = Color.Yellow,
            disabledUncheckedTrackColor = Color.Yellow
        )
    )

}

@Composable
fun MyDivider() {
    /*
    Divider = Un divisor es una línea delgada que agrupa el contenido en listas y diseños.
    */
    Divider(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), color = Color.Red
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyBadgeBox() {
    /*
    BadgeBox = Un BadgeBox se usa para decorar el contenido con una insignia que puede contener
    información dinámica, como la presencia de una nueva notificación o una cantidad
    de solicitudes pendientes. Las insignias pueden ser solo íconos o contener texto breve.

    se divide en 2 partes: el contenido (el propio circulo) y encima de qué componente estará
    */
    BadgeBox(
        Modifier.padding(16.dp),  //hay que ponerle mas espacio para que aparezca y sea visible
        badgeContent = { Text(text = "100") },  //el contenido del badgeBox. Crece todoo lo que necesitepara que se vea el contenido
        backgroundColor = Color.Blue,
        contentColor = Color.Green
    ) {
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
    }
}

@Composable
fun MyCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()  //todoo el ancho
            .padding(16.dp),
        elevation = 12.dp,  //sombra de elevacion
        shape = MaterialTheme.shapes.small,  //bordeado de las esquinas
        backgroundColor = Color.Red,  //color de fondo
        contentColor = Color.Green,  //colores dentro de la card
        border = BorderStroke(5.dp, Color.Green)  //borde de la card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ejemplo 1")
            Text(text = "Ejemplo 2")
            Text(text = "Ejemplo 3")
        }
    }
}

@Composable
fun MyProgressAdvance() {
    var progressStatus by rememberSaveable { mutableStateOf(0f) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //progress  //indicael porcentaje de la barra de progreso
        LinearProgressIndicator(progress = progressStatus)  //opcion linea
        //CircularProgressIndicator(progress = progressStatus)  //opcion circular

        //botones para incrementar o reducir la barra de progreso
        Row(Modifier.fillMaxWidth()) {
            if (progressStatus < 1)  //opcion para que no sobrepase los limites de la barra
                Button(onClick = { progressStatus += 0.1f }) {
                    Text(text = "Incrementar")
                }
            if (progressStatus > 0.1)  //opcion para que no sobrepase los limites de la barra
                Button(onClick = { progressStatus -= 0.1f }) {
                    Text(text = "Reducir")
                }
        }
    }
}

@Composable
fun MyProgress() {
    //variable de estado
    var showLoading by rememberSaveable { mutableStateOf(false) }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showLoading) {
            CircularProgressIndicator(color = Color.Red, strokeWidth = 10.dp)
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                backgroundColor = Color.Green
            )
        }

        //cada vez que se pulse el boton -> va a cambiar el estado
        Button(onClick = { showLoading = !showLoading }) {
            Text(text = "Cargar perfil")
        }
    }
}

//Icon = similar a Image
@Composable
fun MyIcon() {
    Icon(
        imageVector = Icons.Rounded.Star,
        contentDescription = "Icono",
        tint = Color.Red
    )
}

@Composable
fun MyImageAdvance() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "ejemplo",
        modifier = Modifier
            .clip(CircleShape)  //circulo completo
            //.clip(RoundedCornerShape(25f))  //borde como boton
            .border(5.dp, Color.Red, CircleShape)
    )
}

//Image = ImageView
@Composable
fun MyImage() {
    //Image -> necesita 2 parametros obligatorios
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "ejemplo",
        alpha = 0.5f  //alpha -> crear transparencia
    )
}

@Composable
fun MyButtonExample() {
    var enabled by rememberSaveable { mutableStateOf(true) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        //Button = boton normal
        Button(
            onClick = { enabled = false },
            enabled = enabled,  //estado -> al hacer click se inhabilita (queda gris) -> hacer el rememberSaveable
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Magenta,
                contentColor = Color.Blue
            ),
            border = BorderStroke(5.dp, Color.Green)
        ) {
            Text(text = "Hola")
        }
        //OutlinedButton = otra clase de boton
        OutlinedButton(
            onClick = { enabled = false },
            enabled = enabled,
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Magenta,
                contentColor = Color.Blue,
                disabledBackgroundColor = Color.Blue,
                disabledContentColor = Color.Red
            )
        ) {
            Text(text = "Hola")
        }
        //TextButton = otra clase de boton
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Hola")
        }
    }
}

@Composable
fun MyTextFieldOutlined() {
    /* OutlinedTextField = Los campos de texto con contorno tienen menos énfasis visual
    que los campos de texto rellenos. Cuando aparecen en lugares como formularios,
    donde muchos campos de texto se colocan juntos, su énfasis reducido ayuda a
    simplificar el diseño.

    */
    var myText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = myText,
        onValueChange = { myText = it },
        modifier = Modifier.padding(24.dp),
        label = { Text(text = "Holita") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Magenta,
            unfocusedBorderColor = Color.Blue
        )
    )
}

@Composable
fun MyTextFieldAdvance() {
    //siempre tener un estado para gestionar
    var myText by remember { mutableStateOf("") }

    TextField(
        value = myText,
        onValueChange = {
            myText = if (it.contains("a")) {
                it.replace("a", "")
            } else {
                it
            }
        },
        label = { Text(text = "Introduce tu nombre") })

}

//TextField = EditText
@Composable
fun MyTextField(name: String, onValueChanged: (String) -> Unit) {  //ejemplo 2
    //Se necesitan 2 parametros
    TextField(value = name, onValueChange = { onValueChanged(it) })
    //State hoisting = patron para quitar un estados de los composables -> consiste en sacar el estado a un miembro superior
    //se envia al padre para que ejecute { onValueChanged(it) } en ese nivel.

//fun MyTextField() {  //ejemplo 1
//    var myText by remember { mutableStateOf("Aris") }
//    TextField(value = myText, onValueChange = {myText = it})
}

//Text = TextView
@Composable
fun MyText() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Esto es un ejemplo")
        Text(text = "Esto es un ejemplo", color = Color.Blue)
        Text(text = "Esto es un ejemplo", fontWeight = FontWeight.ExtraBold)
        Text(text = "Esto es un ejemplo", fontWeight = FontWeight.Light)
        Text(text = "Esto es un ejemplo", fontFamily = FontFamily.Cursive)
//        Text(text = "Esto es un ejemplo", style = TextStyle(fontFamily = FontFamily.Cursive))
        Text(
            text = "Esto es un ejemplo",
            textDecoration = TextDecoration.LineThrough
        )
        Text(
            text = "Esto es un ejemplo",
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = "Esto es un ejemplo",
            textDecoration = TextDecoration.combine(
                listOf(TextDecoration.Underline, TextDecoration.LineThrough)
            )
        )
        Text(text = "Esto es un ejemplo", fontSize = 30.sp)
    }
}