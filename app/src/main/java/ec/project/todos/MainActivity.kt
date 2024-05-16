package ec.project.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                val showDialogData by tasksViewModel.showDialog.observeAsState(false)
                val errorMessage by tasksViewModel.errorMessage.observeAsState()
                val tasksList by tasksViewModel.tasksList.observeAsState()
                var task: TaskModel by remember {
                    mutableStateOf(TaskModel())
                }
                val hasSelectedTasks by remember {
                    derivedStateOf {
                        (tasksList?.any { it.selected } ?: false)
                    }
                }
                val fabColor by animateColorAsState(
                    targetValue = if (hasSelectedTasks) {
                        Color.Red
                    } else {
                        FloatingActionButtonDefaults.containerColor
                    }, label = "containerColor"
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        errorMessage.takeIf { it.isNullOrEmpty().not() }?.let {
                            AlertDialog(
                                title = {
                                        Text(text = "An Error Occurred When:")
                                },
                                text = {
                                       Text(text = it)
                                },
                                confirmButton = {
                                    TextButton(onClick = {  tasksViewModel.clearErrorMessage() }) {
                                        Text(text ="OK")
                                    }
                                },
                                onDismissRequest = {  tasksViewModel.clearErrorMessage() }
                            )
                        }
                        tasksList?.let { tasks ->
                            TasksScreen(
                                tasks = tasks,
                                onTaskClick = {
                                    if (it.selected) {
                                        tasksViewModel.updateTask(it.copy(selected = false))
                                    } else {
                                        task = it
                                        tasksViewModel.onShowDialog(true)
                                    }
                                },
                                onTaskLongClick = {
                                    tasksViewModel.updateTask(it.copy(selected = true))
                                }
                            )
                        }
                        TasksCreatorModal(
                            showModal = showDialogData,
                            onSaveClick = {
                                if (task.date > 0L) {
                                    tasksViewModel.updateTask(task)
                                } else {
                                    tasksViewModel.createNewTask(
                                        task.copy(
                                            date = System.currentTimeMillis()
                                        )
                                    )
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
                                if (task.date >= 0L) {
                                    tasksViewModel.deleteTask(task.id)
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
                            containerColor = fabColor,
                            onClick = {
                                if(hasSelectedTasks){
                                    tasksViewModel.removeAllSelectedTasks()
                                } else {
                                    tasksViewModel.onShowDialog(true)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (hasSelectedTasks) {
                                    Icons.Default.Delete
                                } else {
                                    Icons.Default.Add
                                }, contentDescription = null, tint = if (hasSelectedTasks) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
