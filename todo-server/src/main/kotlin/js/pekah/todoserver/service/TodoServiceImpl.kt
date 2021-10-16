package js.pekah.todoserver.service

import js.pekah.todoserver.database.Todo
import js.pekah.todoserver.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(val todoRepository: TodoRepository): TodoService {

    override fun list(): MutableList<Todo>? = todoRepository.findAll()

    override fun read(id: Int): Todo? = todoRepository.findById(id).orElse(null)

    override fun save(todo: Todo): Todo? = todoRepository.save(todo)

    override fun delete(todo: Todo) = todoRepository.delete(todo)

}