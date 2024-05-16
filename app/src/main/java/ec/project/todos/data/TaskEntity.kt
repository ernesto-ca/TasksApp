package ec.project.todos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import ec.project.todos.presentation.TaskModel

@Entity
class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var text: String,
    var date: Long,
    var selected: Boolean = false
) {
    fun toModel(): TaskModel = TaskModel(
        id = id,
        text = text,
        date = date,
        selected = selected
    )
}