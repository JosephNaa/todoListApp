package js.pekah.todoApp.config

import android.app.Application
import js.pekah.todoApp.database.TodoDatabase
import js.pekah.todoApp.repository.TodoRepository

class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()

        TodoRepository.initialize(this)
    }
}