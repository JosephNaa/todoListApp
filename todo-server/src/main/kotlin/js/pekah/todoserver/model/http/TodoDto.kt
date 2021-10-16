package js.pekah.todoserver.model.http

import io.swagger.annotations.ApiModelProperty
import js.pekah.todoserver.database.Todo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotBlank

data class TodoDto (

    @field:ApiModelProperty(
        value = "DB INDEX",
        example = "1",
        required = false
    )
    var id: Int?=null,

    @field:ApiModelProperty(
        value = "일정명",
        example = "일정관리",
        required = true
    )
    @field:NotBlank
    var title: String?=null,

    @field:ApiModelProperty(
        value = "일정설명",
        example = "1시 스타벅스",
        required = false
    )
    var description: String?=null,

    @field:ApiModelProperty(
        value = "시간",
        example = "2021-10-12 00:00:00",
        required = true
    )
    @field:NotBlank
    var schedule: String?=null,

    @field:ApiModelProperty(
        value = "생성일자",
        required = false
    )
    var createdAt: LocalDateTime?=null,

    @field:ApiModelProperty(
        value = "수정일자",
        required = false
    )
    var updatedAt: LocalDateTime?=null
) {
    @AssertTrue(message = "yyyy-MM-dd HH:mm:ss 포맷이 맞지 않습니다.")
    fun isValidSchedule(): Boolean {
        return try {
            LocalDateTime.parse(schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            true
        } catch (e: Exception) {
            false
        }
    }
}

fun TodoDto.convertTodoDto(todo: Todo): TodoDto {
    return TodoDto().apply {
        this.id = todo.id
        this.title = todo.title
        this.description = todo.description
        this.schedule = todo.schedule?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todo.createdAt
        this.updatedAt = todo.updatedAt
    }
}