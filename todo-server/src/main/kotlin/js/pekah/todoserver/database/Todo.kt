package js.pekah.todoserver.database

import js.pekah.todoserver.model.http.TodoDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "todo")
data class Todo (

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?=null,
    var title: String?=null,
    var description: String?=null,
    var schedule: LocalDateTime?=null,
    var createdAt: LocalDateTime?=null,
    var updatedAt: LocalDateTime?=null
)

fun Todo.convertTodo(todoDto: TodoDto): Todo {
    return Todo().apply {
        this.id = todoDto.id
        this.title = todoDto.title
        this.description = todoDto.description
        this.schedule = LocalDateTime.parse(todoDto.schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todoDto.createdAt
        this.updatedAt = todoDto.updatedAt
    }
}