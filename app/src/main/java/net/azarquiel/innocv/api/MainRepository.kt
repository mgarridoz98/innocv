package net.azarquiel.innocv.api

import net.azarquiel.innocv.model.User


class MainRepository() {
    val service = WebAccess.userService

    suspend fun getUsers(): List<User> {
        val webResponse = service.getUsers().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }

    suspend fun saveUser(userN: User){
        val webResponse = service.saveUser(userN).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
    }

    suspend fun modifyUser(userM: User){
        val webResponse = service.modifyUser(userM).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
    }

    suspend fun deleteUser(id : Int){
        val webResponse = service.deleteUser(id).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
    }

}
