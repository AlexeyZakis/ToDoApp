package com.example.todoapp.tests

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todoapp.MainActivity
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.di.LocalStorageQualifier
import com.example.todoapp.presentation.data.ThemeRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockWebServer
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TodoItemsListScreen {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var httpClient: HttpClient

    private val mockWebServer = MockWebServer()

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Inject
    lateinit var themeRepository: ThemeRepository

    @Inject
    @LocalStorageQualifier
    lateinit var testStorage: TaskStorage

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test // Work if by default done tasks are shown
    fun ifInHideDoneTaskModeChangeCompletionTaskWillDisappearOrAppear() = runTest {
        composeTestRule.onRoot().printToLog("TEST_TAG")
        val todoItemCheckBoxes = composeTestRule.onAllNodesWithTag("todoItemCheckBox")
        val firstTodoItemCheckBox = todoItemCheckBoxes.onFirst()
        val hideDoneTaskBtn = composeTestRule.onNodeWithTag("hideDoneTaskBtn")

        val isFirstTodoItemCheckBoxChecked =
            firstTodoItemCheckBox.fetchSemanticsNode()
                .config.getOrNull(SemanticsProperties.ToggleableState)
                .toString()

        val beforeNumOfCheckBoxes = todoItemCheckBoxes.fetchSemanticsNodes().size

        if (isFirstTodoItemCheckBoxChecked == "Off") {
            firstTodoItemCheckBox.performClick()
        }

        hideDoneTaskBtn.performClick()

        val afterNumOfCheckBoxes = todoItemCheckBoxes.fetchSemanticsNodes().size

        assertNotEquals(beforeNumOfCheckBoxes, afterNumOfCheckBoxes)
    }

    @Test
    fun ifAddTodoItemServerReturnSuccess() {
        val addTodoItemBtn = composeTestRule.onNodeWithTag("addTodoItemBtn")
        val todoItemEditText = composeTestRule.onNodeWithTag("todoItemEditText")
        val saveBtn = composeTestRule.onNodeWithTag("saveBtn")

        val newTodoItemText = "New todo item text"

        addTodoItemBtn.performClick()
        todoItemEditText.performTextInput(newTodoItemText)
        saveBtn.performClick()

        runBlocking {
            val response: HttpResponse = httpClient.get(mockWebServer.url("/saveTodo").toString())
            val responseBody: String = response.body<String>()
            assert(responseBody.contains("success"))
        }
    }
}
