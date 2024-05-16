package ec.project.todos.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.project.todos.data.repository.ITodoRepository
import ec.project.todos.data.repository.TodoRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val todoRepository: ITodoRepository
): ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>(false)
    val showDialog: LiveData<Boolean> = _showDialog


    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> = _errorMessage

    val tasksList = todoRepository.getTasks()

    fun onShowDialog (show: Boolean) {
        _showDialog.value = show
    }

    fun createNewTask(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.Main) {
            val isSuccess = todoRepository.saveTask(taskModel)
            if (!isSuccess) {
                _errorMessage.postValue("Error when trying to save new task, try again.")
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val isSuccess =  todoRepository.deleteTask(taskId)
            if (!isSuccess) {
                _errorMessage.postValue("Error when trying to delete the task, try again.")
            }
        }
    }

    fun saveTaskChanges(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.Main) {
            val isSuccess = todoRepository.saveTask(taskModel)
            if (!isSuccess) {
                _errorMessage.postValue("Error when trying to update the task, try again.")
            }
        }
    }

    fun removeAllSelectedTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            tasksList.value?.forEach{
                if (it.selected && _errorMessage.value.isNullOrEmpty()){
                    deleteTask(it.id)
                } else if (_errorMessage.value.isNullOrEmpty()) {
                    return@launch
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.postValue("")
    }
}