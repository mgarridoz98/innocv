package net.azarquiel.innocv.api

import kotlinx.coroutines.Deferred
import net.azarquiel.innocv.model.User
import retrofit2.Response
import retrofit2.http.*

interface InnocvService {
    @GET("user")
    fun getUsers(): Deferred<Response<List<User>>>

    @POST("user")
    fun saveUser(@Body user: User): Deferred<Response<Unit>>

    @PUT("user")
    fun modifyUser(@Body user: User) : Deferred<Response<Unit>>

    @DELETE("user/{id}")
    fun deleteUser(@Path("id") id : Int) : Deferred<Response<Unit>>
}