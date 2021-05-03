package app.basic.project.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.basic.project.network.APIInterface
import app.basic.project.repository.MainRepository
import app.basic.project.viewmodel.MainViewModel

class ViewModelFactory(private val apiInterface: APIInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiInterface)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}