package app.basic.project.network

class APIHelper(private val apiInterface: APIInterface) {

    suspend fun getUsers() = apiInterface.getUsers()
}