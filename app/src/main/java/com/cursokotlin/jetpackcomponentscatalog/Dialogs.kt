package com.cursokotlin.jetpackcomponentscatalog

import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cursokotlin.jetpackcomponentscatalog.ui.theme.JetpackComponentsCatalogTheme

/* no se va a llamar a la fun showDialog como antes.
ahora el dialogo se va a estar mostrando y se ocultara/mostrara de acuerdo a un mutableState

*/

@Composable
fun MyConfirmationDialog(
    show: Boolean,
    onDismiss: () -> Unit,
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(shape = MaterialTheme.shapes.small) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                MyTitleDialog(text = "Phone ringtone", modifier = Modifier.padding(24.dp))
                Divider(Modifier.fillMaxWidth(), Color.LightGray)
                var status by remember { mutableStateOf("") }
                MyRadioButtonList(status) { status = it }  //solo puede estar marcado 1 solo de todos
                Divider(Modifier.fillMaxWidth(), Color.LightGray)
                Row(Modifier
                    .align(Alignment.End)
                    .padding(8.dp)) {
                    TextButton(onClick = { }) {
                        Text(text = "CANCEL")
                    }
                    TextButton(onClick = { }) {
                        Text(text = "OK")
                    }
                }    }
            }
        }
    }
    /* en cada item del RadioButtonList poner estos padding:
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 16.dp)) {
            RadioButton(selected = name == "Aris",
                onClick = { onItemSelected("Aris") },
                colors = RadioButtonDefaults.colors(selectedColor = Purple500))
            Text(
                text = "Aris",
                modifier = Modifier.padding(start = 24.dp))
        }
    */
    /*
    setContent {
        JetpackComponentsCatalogTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                var show by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { show = true }) {
                        Text(text = "Mostrar dialogo")
                    }
                    MyConfirmationDialog(
                        show = show,
                        onDismiss = { show = false })
                }
            }
        }
    }
     */
}

@Composable
fun MyCustomDialog(
    show: Boolean,
    onDismiss: () -> Unit,
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                MyTitleDialog("Set backup account")
                AccountItem("ejemplo1@gmail.com", R.drawable.avatar)
                AccountItem("ejemplo2@gmail.com", R.drawable.avatar)
                AccountItem("Añadir nueva cuenta", R.drawable.add)
            }
        }
    }
}

@Composable
fun MySimpleCustomDialog(
    show: Boolean,
    onDismiss: () -> Unit,
) {
    if (show) {
        /* Dialog = Abre un diálogo con el contenido dado. El cuadro de diálogo es visible siempre que
        forme parte de la jerarquía de composición. Para permitir que el usuario descarte el diálogo,
        la implementación de onDismissRequest debe contener una forma de eliminar el diálogo de
        la jerarquía de composición.
        */

        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = false,  //boton de ATRAS desaparezca -> true o flase
                dismissOnClickOutside = false)  //cuando se clickea fuera del dialog
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()  //ocupa todoo el ancho menos el padding de 24.dp
            ) {
                Text(text = "Esto es un ejemplo")
                Text(text = "Esto es un ejemplo")
                Text(text = "Esto es un ejemplo")
            }
        }
    }
}

@Composable
fun MyAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (show) {
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = "Título") },
            text = { Text(text = "Hola, soy una descripción super guay :(") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = "ConfirmButton")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "DismissButton")
                }
            }
        )
    }
}

@Composable
fun AccountItem(email: String, @DrawableRes drawable: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding( horizontal = 24.dp, vertical = 16.dp)
                .size(40.dp)
                .clip(CircleShape)
        )

        Text(
            text = email,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 24.dp))
    }

}

@Composable
fun MyTitleDialog(text: String, modifier: Modifier = Modifier.padding(bottom = 12.dp)) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        modifier = modifier  //se usa asi para pasarlo por marametro al modifier y reutilizarlo como esta en el constructor
    )
}

@Composable
fun MyDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    /* AlertDialog = Los diálogos de alerta interrumpen a los usuarios con información, detalles o acciones urgentes.
    */
    if (show) {
        AlertDialog(onDismissRequest = { onDismiss() },  //onDismissRequest = para cuando el usuario clickea fuera del dialogo
            title = { Text(text = "Titulo") },  //aca se puede poner un texto, imagen, buton, cualqueir cosa
            text = { Text(text = "Soy una descripcion super guay :)") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = "ConfirmButton")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "DismissButton")
                }
            })
    }

    /*        setContent {
            JetpackComponentsCatalogTheme {
                /*
                se uso esto para el radiobuttonlist
                 var selected by remember {
                     mutableStateOf("Aris")
                 }
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var show by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = { show = true }) {
                            Text(text = "Mostrar dialogo")
                        }
                        MyDialog(show = show,
                            onDismiss = { show = false },
                            onConfirm = { Log.i("aris", "click") })
                    }
                }

            }
        }
    */
}