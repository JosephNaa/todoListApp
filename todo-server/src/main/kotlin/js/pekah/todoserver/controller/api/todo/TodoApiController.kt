package js.pekah.todoserver.controller.api.todo

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import js.pekah.todoserver.database.Todo
import js.pekah.todoserver.database.convertTodo
import js.pekah.todoserver.model.http.TodoDto
import js.pekah.todoserver.model.http.convertTodoDto
import js.pekah.todoserver.service.TodoServiceImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "일정관리")
@RestController
@RequestMapping("/api/todo")
class TodoApiController(val todoService: TodoServiceImpl) {

    @ApiOperation(value = "일정확인", notes = "일정 확인 GET API")
    @GetMapping(path = [""])
    fun read(
        @RequestParam(required = false) id: Int?): ResponseEntity<Any?> {

        return id?.let {
            todoService.read(it)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/api/todo/all")
                .build()
        }
    }

    @GetMapping(path = ["/all"])
    fun list(): MutableList<TodoDto>? {
        return todoService.list()?.map {
            TodoDto().convertTodoDto(it)
        }?.toMutableList()
    }

    @PostMapping(path = [""])
    fun create(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoService.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    @PutMapping(path = [""]) // create = 201, update = 200
    fun update(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoService.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    @DeleteMapping(path = ["/{index}"])
    fun delete(@PathVariable(name = "index") _index: Int): ResponseEntity<Any> {
        var delItem: Todo? = todoService.read(_index)
        if (delItem != null) {
            todoService.delete(delItem)
            return ResponseEntity.status(500).build()
        }

        return ResponseEntity.ok().build()
    }
}