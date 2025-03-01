package com.example.chatia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatia.ui.theme.ChatIATheme
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Activa modo oscuro manualmente
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        enableEdgeToEdge()
        setContent {
            ChatIATheme {
                ChatScreen()
            }
        }
    }
}

@Composable
fun ChatScreen() {
    var mensaje by remember { mutableStateOf("") }
    var mensajes by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // 🔥 Fondo negro manual
            .padding(16.dp)
            .imePadding() // Evita que el teclado oculte el TextField
            .navigationBarsPadding() // Evita que la barra de navegación tape la UI
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(mensajes.reversed()) { (text, isUser) ->
                ChatBubble(text = text, isUser = isUser)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                placeholder = { Text("Escribe tu mensaje...", color = Color.White) }, // 🔥 Texto blanco
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray, // 🔥 Caja gris oscura
                    unfocusedContainerColor = Color.Gray,
                    cursorColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            Button(
                onClick = {
                    if (mensaje.isNotBlank()) {
                        mensajes = mensajes + (mensaje to true)
                        enviarMensaje(mensaje) { respuestaIA ->
                            mensajes = mensajes + (respuestaIA to false)
                        }
                        mensaje = ""
                    }
                }
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (isUser) Color.Blue else Color.DarkGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            textAlign = TextAlign.Start
        )
    }
}

fun enviarMensaje(mensaje: String, onResponse: (String) -> Unit) {
    RetrofitClient.iaService.enviarMensaje(mensaje).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val rawResponse = response.body()?.string()
                val respuestaLimpia = try {
                    JSONObject(rawResponse).getString("respuesta") // Extrae solo la respuesta
                } catch (e: Exception) {
                    "Error en la respuesta"
                }
                onResponse(respuestaLimpia)
            } else {
                onResponse("Error en la respuesta: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            onResponse("Error en la solicitud: ${t.message}")
        }
    })
}
