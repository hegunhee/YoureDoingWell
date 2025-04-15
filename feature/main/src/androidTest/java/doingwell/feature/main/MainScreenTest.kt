package doingwell.feature.main

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.hegunhee.model.user.UserData
import doingwell.feature.main.screen.MainScreen
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenEmptyState_whenScreening_shownTitleText() {
        sut.setContent {
            ShowMainScreen()
        }

        sut.onNodeWithText("당신은\n잘하고 있다")
            .assertIsDisplayed()
    }

    @Composable
    private fun ShowMainScreen(
        userData: UserData? = null,
        modifier: Modifier = Modifier,
    ) {
        MainScreen(
            userData = userData,
            modifier = modifier,
        )
    }
}