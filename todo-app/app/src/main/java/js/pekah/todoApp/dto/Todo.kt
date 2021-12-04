package js.pekah.todoApp.dto

data class Todo(
    var id: Long,
    val title: String,
    val content: String,
    val timestamp: String,
    var isChecked: Boolean
) {
    constructor(): this(0, "", "", "", false)
}