package js.pekah.todoserver.service

import js.pekah.todoserver.database.Todo
import js.pekah.todoserver.database.convertTodo
import js.pekah.todoserver.model.http.TodoDto
import js.pekah.todoserver.model.http.convertTodoDto
import js.pekah.todoserver.repository.TodoRepositoryImpl
import org.springframework.stereotype.Service

@Service
class TodoService(val todoRepositoryImpl: TodoRepositoryImpl) {

    fun create(todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    fun read(index: Int): TodoDto? {
        return todoRepositoryImpl.findOne(index)?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    fun readAll(): MutableList<TodoDto> {
        return todoRepositoryImpl.findAll()
            .map {
                TodoDto().convertTodoDto(it)
            }.toMutableList()
    }

    fun update(todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    fun delete(index: Int): Boolean {
        return todoRepositoryImpl.delete(index)
    }
}