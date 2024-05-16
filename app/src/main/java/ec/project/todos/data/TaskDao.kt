package ec.project.todos.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): LiveData<List<TaskEntity>>

    @Insert
    suspend fun saveTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}