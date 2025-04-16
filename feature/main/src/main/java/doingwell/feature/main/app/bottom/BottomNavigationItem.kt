package doingwell.feature.main.app.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.hegunhee.daily.navigation.DAILY_ROUTE
import doingwell.feature.addRecord.navigation.ADD_RECORD_ROUTE
import doingwell.feature.main.R

sealed class BottomNavigationItem(
    val title: Int,
    val icon: ImageVector,
    val screenRoute: String,
) {
    data object Daily : BottomNavigationItem(R.string.daily, Icons.Default.Home, DAILY_ROUTE)
    data object AddRecord : BottomNavigationItem(R.string.add, Icons.Default.Add, ADD_RECORD_ROUTE)
    data object Setting : BottomNavigationItem(R.string.setting, Icons.Default.Settings, SETTING_ROUTE)

    companion object {
        val items = listOf(Daily, AddRecord, Setting)
    }
}

const val SETTING_ROUTE = "SETTING"
