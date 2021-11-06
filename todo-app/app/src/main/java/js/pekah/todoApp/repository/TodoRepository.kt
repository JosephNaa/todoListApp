package js.pekah.todoApp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import js.pekah.todoApp.database.TodoDatabase
import js.pekah.todoApp.dto.Todo
import java.lang.IllegalStateException

private const val DATABASE_NAME = "todo-database.db"
class TodoRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val todoDao = database.todoDao()

    fun list(): LiveData<MutableList<Todo>> = todoDao.list()

    fun getTodo(id: Long): LiveData<Todo> = todoDao.selectOne(id)

    suspend fun insert(dto: Todo) = todoDao.insert(dto)

    fun update(dto: Todo) = todoDao.update(dto)

    fun delete(dto: Todo) = todoDao.delete(dto)

    companion object {
        private var INSTANCE: TodoRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TodoRepository(context)
            }
        }

        fun get(): TodoRepository {
            return INSTANCE ?:
            throw IllegalStateException("TodoRepository must be initialized")
        }
    }
}