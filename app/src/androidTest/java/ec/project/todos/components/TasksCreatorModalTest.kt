package ec.project.todos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import ec.project.todos.presentation.TasksCreatorModal
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksCreatorModalTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun whenWrite_thenInputISFilled() {
        // Given
        val text = "Hello world"

        // When
        composeRule.setContent {
            Column {
                TasksCreatorModal(
                    showModal = true,
                    onSaveClick = { },
                    onCloseClick = { },
                    value = text)
            }
        }
        // Then
        composeRule.onNode(hasSetTextAction()).assert(hasText(text))
    }

    @Test
    fun whenOpen_thenCloseIt() {
        // When
        composeRule.setContent {
            var isSaved by remember {
                mutableStateOf(true)
            }
            Column {
                TasksCreatorModal(
                    showModal = isSaved,
                    onSaveClick = { isSaved = false },
                    onCloseClick = { })
            }
        }
        composeRule.onNodeWithTag("CreatorModalSave").performClick()
        // Then
        composeRule.onNodeWithTag("TaskCreatorModalContent").assertIsNotDisplayed()
    }
    @Test
    fun whenOpen_OnEdit_thenCheckOptions() {
        // When
        composeRule.setContent {
            var isSaved by remember {
                mutableStateOf(true)
            }
            Column {
                TasksCreatorModal(
                    showModal = isSaved,
                    isEditMode = true,
                    onSaveClick = { isSaved = false },
                    onCloseClick = { })
            }
        }
        composeRule.onNodeWithTag("CreatorModalOptions").performClick()
        composeRule.onNodeWithTag("CreatorModalSaveOption").performClick()
        // Then
        composeRule.onNodeWithTag("TaskCreatorModalContent").assertIsNotDisplayed()
    }

}