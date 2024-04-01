package ec.project.todos.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties


@Composable
fun TasksCreatorModal(
    showModal: Boolean,
    onSaveClick: () -> Unit,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit = {},
    isEditMode: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var isDropdownOpen by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = showModal,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onCloseClick) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                    if (isEditMode) {
                        IconButton(onClick = { isDropdownOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null
                            )
                            DropdownMenu(
                                expanded = isDropdownOpen,
                                properties = PopupProperties(dismissOnBackPress = true),
                                onDismissRequest = { isDropdownOpen = false }
                            ) {
                                DropdownMenuItem(text = {
                                    Text(text = "Save")
                                }, onClick = onSaveClick)
                                DropdownMenuItem(text = {
                                    Text(text = "Delete")
                                }, onClick = onDeleteClick)
                            }
                        }
                    } else {
                        IconButton(onClick = onSaveClick) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = null)
                        }
                    }
                }
                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    value = value,
                    onValueChange = onValueChange
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskCreatorDialogPreview() {
    Surface(Modifier.background(Color.White)) {
        TasksCreatorModal(true, {}, {})
    }
}