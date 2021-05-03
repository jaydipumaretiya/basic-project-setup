package app.basic.project.repository

import app.basic.project.network.APIInterface

class MainRepository(private val apiInterface: APIInterface) {
    suspend fun getUsers() = apiInterface.getUsers()
}