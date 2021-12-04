package js.pekah.todoApp.util

import js.pekah.todoApp.api.TodoApi
import js.pekah.todoApp.config.ApplicationClass

class RetrofitUtil {
    companion object {
        val todoService = ApplicationClass.retrofit.create(TodoApi::class.java)
    }
}