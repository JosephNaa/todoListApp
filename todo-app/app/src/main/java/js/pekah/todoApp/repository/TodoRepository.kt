package js.pekah.todoApp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import js.pekah.todoApp.api.TodoApi
import js.pekah.todoApp.dto.Todo
import js.pekah.todoApp.util.RetrofitUtil
import java.lang.IllegalStateException

class TodoRepository constructor(private val todoApi: TodoApi){

    suspend fun list() = todoApi.getTodoList()

    suspend fun getTodo(id: Long) = todoApi.getTodo(id)

    suspend fun create(dto: Todo) = todoApi.createTodo(dto)

    suspend fun update(id: Long) = todoApi.updateTodo(id)

    suspend fun delete(id: Long) = todoApi.deleteTodo(id)

}