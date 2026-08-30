package com.example.tallerlayoutsylistas.data.remote.api

import android.util.Log
import com.example.tallerlayoutsylistas.data.remote.model.User
import com.example.tallerlayoutsylistas.data.remote.model.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorApiClient {

    private val client = HttpClient(OkHttp) {

        defaultRequest {
            url("https://dummyjson.com/")
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getUsers(): Result<List<User>> {
        return try {
            val response: UsersResponse = client
                .get("users?limit=120")
                .body()
            Result.success(response.users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
