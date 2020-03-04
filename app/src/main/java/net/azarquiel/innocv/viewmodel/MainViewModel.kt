package net.azarquiel.innocv.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.innocv.api.MainRepository
import net.azarquiel.innocv.model.User

class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getUsers(): LiveData<List<User>> {
        val dataUser = MutableLiveData<List<User>>()
        GlobalScope.launch(Main) {
            dataUser.value = repository.getUsers()
        }
        return dataUser
    }

    fun saveUser( userN: User):LiveData<Unit> {
        val v= MutableLiveData<Unit>()
        GlobalScope.launch(Main) {
            v.value = repository.saveUser(userN)
        }
        return v
    }
    fun modifyUser( userM: User):LiveData<Unit> {
        val v= MutableLiveData<Unit>()
        GlobalScope.launch(Main) {
            v.value = repository.modifyUser(userM)
        }
        return v
    }

    fun deleteUser( id: Int):LiveData<Unit> {
        val v= MutableLiveData<Unit>()
        GlobalScope.launch(Main) {
            v.value = repository.deleteUser(id)
        }
        return v
    }
}
