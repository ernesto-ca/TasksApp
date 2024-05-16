package ec.project.todos.presentation

import ec.project.todos.data.TaskEntity

data class TaskModel(
    val id: Int = 0,
    val text: String = "", val date: Long = 0L, val selected: Boolean = false
){
    fun toEntity(): TaskEntity = TaskEntity(
        id = id,
        text = text,
        date = date,
        selected = selected
    )
}
