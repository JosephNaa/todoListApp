package js.pekah.todoserver.model.http

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.validation.FieldError
import javax.validation.Validation

class TodoDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoTdoTest() {
        val todoTdo = TodoDto().apply {
            this.title = "테스트"
            this.description = ""
            this.schedule = "2021-10-13 00:00:00"
        }

        val result = validator.validate(todoTdo)

        Assertions.assertEquals(true, result.isEmpty())
    }
}