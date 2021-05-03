package app.basic.project.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.basic.project.model.User
import app.basic.project.repository.MainRepository
import app.basic.project.util.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class MainViewModel(
    app: Application,
    private val mainRepository: MainRepository
) : ViewModel() {

    val users: MutableLiveData<Resource<User>> = MutableLiveData()

    init {
        getUsers()
    }

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> emit(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "IOException Error Occurred!"
                    )
                )
                else -> emit(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Conversion Error Occurred!"
                    )
                )
            }
        }
    }
}