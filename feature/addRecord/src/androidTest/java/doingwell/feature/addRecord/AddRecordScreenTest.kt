package doingwell.feature.addRecord

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.hegunhee.model.user.DateTimeInfo
import com.hegunhee.model.user.UserData
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddRecordScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = sut.activity.baseContext
    }

    @Test
    fun givenPhoto_whenDelete_shownDeletePhoto() {
        // given
        sut.setContent {
            var photos by remember { mutableStateOf(listOf(getPhotos()[0])) }

            val onClickDeletePhoto: (String) -> Unit = { uri ->
                photos = photos.filterNot { it == uri }
            }

            CreateAddRecordScreen(
                photos = photos,
                onClickDeletePhoto = onClickDeletePhoto
            )
        }
        // when
        sut
            .onNodeWithContentDescription(context.getString(doingwell.core.ui.R.string.delete_photo))
            .performClick()

        // shown
        sut
            .onNodeWithContentDescription("사진" + getPhotos()[0])
            .assertIsNotDisplayed()
    }

    @Test
    fun givenTime_whenClickTimeAdd_thenAddTime() {
        val time = createDateTimeInfo()
        val endTime = createDateTimeInfo().copy(minute = 10)
        // given
        sut.setContent {
            val startedAt by remember { mutableStateOf(time) }
            var endedAt by remember { mutableStateOf(endTime) }

            val onClickAddTime: (Int, Int) -> Unit = { hour, minute ->
                endedAt = endedAt.copy(endTime.hour + hour, minute = endTime.minute + minute)
            }

            CreateAddRecordScreen(
                startedAt = startedAt,
                endedAt = endedAt,
                onClickAddTime = onClickAddTime
            )
        }

        sut
            .onNodeWithText(endTime.getTimeText())
            .assertIsDisplayed()

        sut
            .onNodeWithText("+10분")
            .performClick()

        sut
            .onNodeWithText(endTime.copy(minute = endTime.minute+10).getTimeText())
            .assertIsDisplayed()
    }

    @Test
    fun givenTime_whenClickResetTime_thenStartTimeEndTimeEqual() {
        val time = createDateTimeInfo()
        val endTime = createDateTimeInfo().copy(minute = 10)
        // given
        sut.setContent {
            val startedAt by remember { mutableStateOf(time) }
            var endedAt by remember { mutableStateOf(endTime) }

            val onClickResetTime: () -> Unit = {
                endedAt = startedAt
            }

            CreateAddRecordScreen(
                startedAt = startedAt,
                endedAt = endedAt,
                onClickResetTime = onClickResetTime
            )
        }

        sut
            .onNodeWithText(endTime.getTimeText())
            .assertIsDisplayed()

        sut
            .onNodeWithText("초기화")
            .assertIsDisplayed()
            .performClick()

        sut
            .onNodeWithText(endTime.getTimeText())
            .assertIsNotDisplayed()
    }


    @Composable
    private fun CreateAddRecordScreen(
        userData: UserData = UserData(),
        photos: List<String> = listOf(),
        title: String = "",
        description: String = "",
        startedAt: DateTimeInfo = createDateTimeInfo(),
        endedAt: DateTimeInfo = createDateTimeInfo(),
        onClickAddPhoto: (maxPhotoCount: Int, currentPhotoCount: Int) -> Unit = { _, _ -> },
        onClickDeletePhoto: (String) -> Unit = {},
        onTitleTextChanged: (String) -> Unit = {},
        onDescriptionTextChanged: (String) -> Unit = {},
        onClickSaveButton: (title: String, description: String, userId: String) -> Unit = { _, _, _ -> },
        onClickAddTime: (Int, Int) -> Unit = { _, _ -> },
        onClickResetTime: () -> Unit = {},
    ) {
        AddRecordScreen(
            paddingValues = PaddingValues(),
            userData = userData,
            photos = photos,
            title = title,
            description = description,
            startedAt = startedAt,
            endedAt = endedAt,
            onClickAddPhoto = onClickAddPhoto,
            onclickDeletePhoto = onClickDeletePhoto,
            onTitleTextChanged = onTitleTextChanged,
            onDescriptionTextChanged = onDescriptionTextChanged,
            onClickSaveButton = onClickSaveButton,
            onClickAddTime = onClickAddTime,
            onClickReturnTime = onClickResetTime,
        )
    }

    private fun createDateTimeInfo(): DateTimeInfo {
        return DateTimeInfo(
            year = 2025,
            month = 6,
            dayOfMonth = 20,
            hour = 0,
            minute = 0,
        )
    }

    private fun getPhotos(): List<String> {
        return listOf(
            "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
            "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e",
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330"
        )
    }
}
