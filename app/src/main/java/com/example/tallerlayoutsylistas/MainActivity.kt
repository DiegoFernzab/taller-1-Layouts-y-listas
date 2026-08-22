package com.example.tallerlayoutsylistas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.tallerlayoutsylistas.data.remote.api.KtorApiClient
import com.example.tallerlayoutsylistas.data.remote.model.User
import com.example.tallerlayoutsylistas.ui.navigation.DetailRoute
import com.example.tallerlayoutsylistas.ui.navigation.ListRoute
import com.example.tallerlayoutsylistas.ui.screens.DetailScreen
import com.example.tallerlayoutsylistas.ui.screens.ListScreen
import com.example.tallerlayoutsylistas.ui.theme.TallerLayoutsYListasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // Arranca siguiendo al sistema, pero el switch manda a partir de ahí.
    val systemDark = isSystemInDarkTheme()
    var darkTheme by rememberSaveable { mutableStateOf(systemDark) }

    TallerLayoutsYListasTheme(darkTheme = darkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Usuarios") },
                    actions = {
                        Switch(
                            checked = darkTheme,
                            onCheckedChange = { darkTheme = it }
                        )
                    }
                )
            }
        ) { innerPadding ->
            AppContent(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    // Estado: la lista que se le pasa a las pantallas. Empieza vacía.
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    // Pila de navegación: arranca en la lista. Añadir = ir; quitar = volver.
    val backStack = rememberNavBackStack(ListRoute)

    // Se ejecuta una sola vez y llena el estado con lo que responda la API.
    LaunchedEffect(Unit) {
        KtorApiClient().getUsers()
            .onSuccess { users = it }
            .onFailure { Log.e("MainActivity", "Error: ${it.message}") }
    }

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<ListRoute> {
                ListScreen(
                    users = users,
                    onUserClick = { user -> backStack.add(DetailRoute(user.id)) }
                )
            }
            entry<DetailRoute> { route ->
                val user = users.find { it.id == route.userId }
                if (user != null) {
                    DetailScreen(user = user)
                }
            }
        }
    )
}
