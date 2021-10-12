package js.pekah.todoserver.repository

import js.pekah.todoserver.config.AppConfig
import js.pekah.todoserver.database.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @Test
    fun saveTest() {
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        Assertions.assertEquals(1, result.index)
        Assertions.assertNotNull(result.createdAt)
        Assertions.assertNotNull(result.updatedAt)
        Assertions.assertEquals("테스트 일정", result.title)
        Assertions.assertEquals("테스트", result.description)
    }
}