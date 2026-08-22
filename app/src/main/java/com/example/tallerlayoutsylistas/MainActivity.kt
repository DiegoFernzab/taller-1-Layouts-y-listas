package com.example.tallerlayoutsylistas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tallerlayoutsylistas.data.remote.api.KtorApiClient
import com.example.tallerlayoutsylistas.ui.theme.TallerLayoutsYListasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallerLayoutsYListasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // TODO (TEMPORAL): prueba del cliente — borrar cuando Persona B integre la UI
                    val apiClient = KtorApiClient()
                    LaunchedEffect(key1 = Unit) {
                        val result = apiClient.getUsers()
                        result
                            .onSuccess { users ->
                                Log.d("KtorTest", "✅ Usuarios obtenidos: ${users.size}")
                                Log.d("KtorTest", "Primer usuario: ${users.first().firstName} ${users.first().lastName}")
                            }
                            .onFailure { error ->
                                Log.e("KtorTest", "❌ Error: ${error.message}")
                            }
                    }

                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TallerLayoutsYListasTheme {
        Greeting("Android")
    }
}
