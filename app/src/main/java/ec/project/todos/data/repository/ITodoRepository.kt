package ec.project.todos.data.repository

import androidx.lifecycle.LiveData
import ec.project.todos.presentation.TaskModel

interface ITodoRepository {
    fun getTasks(): LiveData<List<TaskModel>>

    suspend fun addTask(taskModel: TaskModel): Boolean

    suspend fun updateTask(taskModel: TaskModel): Boolean
    suspend fun deleteTask(taskId: Int): Boolean
}