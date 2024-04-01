package ec.project.todos.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(): ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>(false)
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasksList = mutableStateListOf<TaskModel>()
    val tasksList: List<TaskModel> = _tasksList

    fun onShowDialog (show: Boolean) {
        _showDialog.value = show
    }

    fun createNewTask(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.Main) {
            _tasksList.add(taskModel)
        }
    }

    fun deleteTask(taskModelDate: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val index = _tasksList.indexOfFirst { it.date == taskModelDate }
            _tasksList.removeAt(index)
        }
    }

    fun saveTaskChanges(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.Main) {
            val index = _tasksList.indexOf(taskModel)
            _tasksList[index]
        }
    }
}