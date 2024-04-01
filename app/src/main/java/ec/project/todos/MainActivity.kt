package ec.project.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ec.project.todos.presentation.TaskModel
import ec.project.todos.presentation.TasksCreatorModal
import ec.project.todos.presentation.TasksScreen
import ec.project.todos.presentation.TasksViewModel
import ec.project.todos.ui.theme.TODOsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tasksViewModel: TasksViewModel by viewModels()
        setContent {
            TODOsTheme {
                // A surface container using the 'background' color from the theme

                val showDialogData by tasksViewModel.showDialog.observeAsState(false)
                val tasksList = tasksViewModel.tasksList
                var task: TaskModel by remember {
                    mutableStateOf(TaskModel())
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)) {
                        TasksScreen(
                            tasks = tasksList,
                            onTaskClick = {
                                if (it.selected) {
                                    tasksViewModel.saveTaskChanges(it.copy(selected = false))
                                } else {
                                    task = it
                                    tasksViewModel.onShowDialog(true)
                                }
                            },
                            onTaskLongClick = {
                                tasksViewModel.saveTaskChanges(it.copy(selected = true))
                            }
                        )
                        TasksCreatorModal(
                            showModal = showDialogData,
                            onSaveClick = {
                                if (task.date > 0L){
                                    tasksViewModel.saveTaskChanges(task)
                                } else {
                                    tasksViewModel.createNewTask(task.copy(
                                        date = System.currentTimeMillis()
                                    ))
                                }
                                tasksViewModel.onShowDialog(false)
                                task = TaskModel()
                            },
                            onCloseClick = {
                                tasksViewModel.onShowDialog(false)
                                task = TaskModel()
                            },
                            onDeleteClick = {
                                tasksViewModel.onShowDialog(false)
                                if (task.date >= 0L){
                                    tasksViewModel.deleteTask(task.date)
                                }
                                task = TaskModel()
                            },
                            value = task.text,
                            isEditMode = task.date > 0L,
                            onValueChange = {
                                task = task.copy(
                                    text = it
                                )
                            }
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.BottomEnd),
                            onClick = { tasksViewModel.onShowDialog(true) }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}
