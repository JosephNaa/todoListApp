package js.pekah.todoserver.repository

import js.pekah.todoserver.database.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Int> {

    override fun findAll(): MutableList<Todo>
}