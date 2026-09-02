package com.example.tallerlayoutsylistas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

    TallerLayoutsYListasTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.usuarios)) },

                )
            }
        ) { innerPadding ->
            AppContent(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    val logTag = stringResource(R.string.mainactivity)

    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    val backStack = rememberNavBackStack(ListRoute)

    LaunchedEffect(Unit) {
        KtorApiClient().getUsers()
            .onSuccess { users = it }
            .onFailure { error ->
                Log.e(logTag, error.message, error)
            }
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
