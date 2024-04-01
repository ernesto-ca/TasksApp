package ec.project.todos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TasksScreen(
    tasks: List<TaskModel>,
    onTaskClick: (TaskModel) -> Unit,
    onTaskLongClick: (TaskModel) -> Unit
) {
    val lazyVerticaGridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        columns = GridCells.Adaptive(120.dp),
        state = lazyVerticaGridState
    ) {
        items(tasks) { task ->
            TaskItem(taskModel = task, onClick = onTaskClick, onLongClick = onTaskLongClick)
        }
    }
}

@Preview
@Composable
fun TasksScreenPreview() {
    TasksScreen(
        listOf(
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac dui nec ligula facilisis scelerisque. Pellentesque eget quam nulla. Donec non semper justo, a lacinia nisi. Cras quis pharetra ligula.",
                date = 0L
            ),
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                date = 1L
            ),
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac dui nec ligula facilisis scelerisque. Pellentesque eget quam nulla. Donec non semper justo, a lacinia nisi. Cras quis pharetra ligula.",
                date = 2L
            ),
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                date = 3L
            )
        ),
        {},
        {}
    )
}

