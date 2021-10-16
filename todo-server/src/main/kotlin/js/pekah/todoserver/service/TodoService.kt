package js.pekah.todoserver.service

import js.pekah.todoserver.database.Todo
import org.springframework.stereotype.Service

@Service
interface TodoService {

    fun list(): List<Todo>?

    fun read(id: Int): Todo?

    fun save(todo: Todo): Todo?

    fun delete(todo: Todo)
}