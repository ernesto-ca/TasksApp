package ec.project.todos.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ec.project.todos.data.TaskDao
import ec.project.todos.presentation.TaskModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImp @Inject constructor(
    private val taskDao: TaskDao
): ITodoRepository{
    override fun getTasks(): LiveData<List<TaskModel>> {
        return taskDao.getTasks().map {
                tasks ->
            tasks.map {
                    task ->
                task.toModel()
            }
        }
    }

    override suspend fun saveTask(taskModel: TaskModel): Boolean {
        return try {
            taskDao.saveTask(taskModel.toEntity())
            true
        } catch (e: Exception){
            Log.e(this::class.java.toString(), e.message.toString())
            false
        }
    }

    override suspend fun deleteTask(taskId: Int): Boolean {
        return try {
            taskDao.deleteTask(taskId)
            true
        } catch (e: Exception){
            Log.e(this::class.java.toString(), e.message.toString())
            false
        }
    }
}