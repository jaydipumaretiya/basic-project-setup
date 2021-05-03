package app.basic.project.network

import app.basic.project.model.User
import retrofit2.http.GET

interface APIInterface {

    @GET("users")
    suspend fun getUsers(): List<User>
}