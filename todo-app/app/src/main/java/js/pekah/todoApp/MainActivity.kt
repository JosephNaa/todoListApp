package js.pekah.todoApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import js.pekah.todoApp.adapter.TodoAdapter
import js.pekah.todoApp.dao.TodoDao
import js.pekah.todoApp.databinding.ActivityMainBinding
import js.pekah.todoApp.dto.Todo
import js.pekah.todoApp.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_todo"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todoAdapter: TodoAdapter
    lateinit var todoRepository: TodoRepository
    lateinit var list: LiveData<MutableList<Todo>>
    var todoList: MutableList<Todo>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoRepository = TodoRepository.get()

        list = todoRepository.list()
        list.observe(this) { it?.let {
            todoAdapter.update(it)
        } }

        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val todo = it.data?.getSerializableExtra("todo") as Todo

            CoroutineScope(Dispatchers.IO).launch {
                todoRepository.insert(todo)
            }
            Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}