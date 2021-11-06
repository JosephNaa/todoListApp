package js.pekah.todoApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import js.pekah.todoApp.databinding.ActivityEditTodoBinding
import js.pekah.todoApp.dto.Todo
import java.text.SimpleDateFormat

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTodoBinding
    private var todo: Todo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        if (type.equals("ADD")) {
            binding.btnSave.text = "추가하기"
        } else {
            todo = intent.getSerializableExtra("item") as Todo?
            binding.etTodoTitle.setText(todo!!.title)
            binding.etTodoContent.setText(todo!!.content)
            binding.btnSave.text = "수정하기"
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTodoTitle.text.toString()
            val content = binding.etTodoContent.text.toString()
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

            val todo = Todo(title, content, currentDate)
            if (type.equals("ADD")) {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val intent = Intent().apply {
                        putExtra("todo", todo)
                        putExtra("flag", 0)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            } else {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val intent = Intent().apply {
                        putExtra("todo", todo)
                        putExtra("flag", 1)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}