package js.pekah.todoApp

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import js.pekah.todoApp.adapter.TodoAdapter
import js.pekah.todoApp.databinding.ActivityMainBinding
import js.pekah.todoApp.dto.Todo
import js.pekah.todoApp.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todoAdapter: TodoAdapter

    lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        todoViewModel.todoList.observe(this) { it
            Log.d(TAG, "onCreate: $it")
            todoAdapter.update(it)
            
        }

        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        todoAdapter.setItemClickListener(object: TodoAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                Toast.makeText(this@MainActivity, "$itemId", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)

                    val intent = Intent(this@MainActivity, EditTodoActivity::class.java).apply {
                        putExtra("type", "EDIT")
                        putExtra("item", todo)
                    }
                    requestActivity.launch(intent)
                }
            }
        })

        todoAdapter.setItemCheckBoxClickListener(object: TodoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)
                    todo.isChecked = !todo.isChecked
                    todoViewModel.update(todo)
                    Log.d(TAG, "onClick: ${todo.isChecked}")
                }
            }
        })
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val todo = it.data?.getSerializableExtra("todo") as Todo

            when(it.data?.getIntExtra("flag", -1)) {
                0 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.insert(todo)
                    }
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Log.d(TAG, "update: ${todo.title}")
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.update(todo)
                    }
                    Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menu_item_delete -> {
                Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()
                todoViewModel.todoList.value!!.forEach {
                    if (it.isChecked) {
                        todoViewModel.delete(it)
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}