package js.pekah.todoserver.repository

import js.pekah.todoserver.database.Todo
import js.pekah.todoserver.database.TodoDataBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl: TodoRepository {

    @Autowired
    lateinit var todoDataBase: TodoDataBase

    override fun save(todo: Todo): Todo {

        return todo.apply {
            this.index = ++todoDataBase.index
            this.createdAt = LocalDateTime.now()
            this.updatedAt = LocalDateTime.now()
        }.run {
            todoDataBase.todoList.add(todo)
            this
        }
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(todo: Todo): Todo {
        TODO("Not yet implemented")
    }

    override fun delete(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun findOne(index: Int): Todo {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Todo> {
        TODO("Not yet implemented")
    }
}