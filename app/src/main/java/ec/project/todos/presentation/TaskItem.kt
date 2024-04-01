package ec.project.todos.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    taskModel: TaskModel,
    onClick: (TaskModel) -> Unit = {},
    onLongClick: (TaskModel) -> Unit = {}
) {
    Box(
        Modifier
            .padding(8.dp)
            .sizeIn(
                minHeight = 60.dp,
                minWidth = 120.dp,
                maxHeight = 190.dp,
                maxWidth = 120.dp
            )
            .clip(RoundedCornerShape(16.dp))
            .wrapContentHeight()
            .combinedClickable(
                onClick = { onClick.invoke(taskModel) },
                onLongClick = {
                    onLongClick.invoke(taskModel)
                },
                onLongClickLabel = "Hold to select"
            )
    ) {

        Text(
            modifier = Modifier
                .background(Color.LightGray)
                .animateContentSize()
                .padding(12.dp),
            text = taskModel.text,
            overflow = TextOverflow.Ellipsis
        )
        if (taskModel.selected) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(Color.Black)
                        .size(16.dp)
                        .clip(CircleShape),
                    imageVector = Icons.Default.Done, contentDescription = null,
                    tint = Color.White
                )
        }

    }
}

@Preview
@Composable
fun TaskItemPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TaskItem(
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac dui nec ligula facilisis scelerisque. Pellentesque eget quam nulla. Donec non semper justo, a lacinia nisi. Cras quis pharetra ligula.",
                date = 0L
            )
        )

        TaskItem(
            TaskModel(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                date = 1L,
                selected = true
            )
        )
    }
}