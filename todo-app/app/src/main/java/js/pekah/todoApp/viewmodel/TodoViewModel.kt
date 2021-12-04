package js.pekah.todoApp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import js.pekah.todoApp.dto.Todo
import js.pekah.todoApp.repository.TodoRepository
import js.pekah.todoApp.util.RetrofitUtil
import kotlinx.coroutines.*

class TodoViewModel: ViewModel() {
    val todoList = MutableLiveData<List<Todo>>()
    var job: Job?=null
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitUtil.todoService.getTodoList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    todoList.postValue(response.body())
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getOne(id: Long): Todo? {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitUtil.todoService.getTodo(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    fun create(dto: Todo) {


    }

    fun update(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }

    fun delete(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.delete(dto)
    }

}