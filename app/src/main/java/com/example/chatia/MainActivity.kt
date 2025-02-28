package com.example.chatia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatia.ui.theme.ChatIATheme
import com.example.chatia.ui.theme.IARequest
import com.example.chatia.ui.theme.IAResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    var respuesta by remember { mutableStateOf("Esperando respuesta...") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Escribe tu mensaje") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                enviarMensaje(mensaje) { respuestaIA ->
                    respuesta = respuestaIA
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(" $respuesta", modifier = Modifier.padding(8.dp))
    }
}
fun enviarMensaje(mensaje: String, onResponse: (String) -> Unit) {
    RetrofitClient.iaService.enviarMensaje(mensaje).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val rawResponse = response.body()?.string()
                onResponse("  $rawResponse")
            } else {
                onResponse("Error en la respuesta: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            onResponse("Error en la solicitud: ${t.message}")
        }
    })
}



@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatIATheme {
        ChatScreen()
    }
}

