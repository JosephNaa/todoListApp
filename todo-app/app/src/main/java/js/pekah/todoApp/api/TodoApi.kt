package js.pekah.todoApp.api

import js.pekah.todoApp.dto.Todo
import retrofit2.Response
import retrofit2.http.*

interface TodoApi {

    @GET("api/todo/all")
    suspend fun getTodoList(): Response<List<Todo>>

    @GET("api/todo/{id}")
    suspend fun getTodo(@Path("id") id: Long): Response<Todo>

    @POST("api/todo")
    suspend fun createTodo(@Path("todo") todo: Todo): Response<Boolean>

    @PUT("api/todo/{id}")
    suspend fun updateTodo(@Path("id") id: Long): Response<Boolean>

    @DELETE("api/todo/{id}")
    suspend fun deleteTodo(@Path("id") id: Long): Response<Boolean>
}