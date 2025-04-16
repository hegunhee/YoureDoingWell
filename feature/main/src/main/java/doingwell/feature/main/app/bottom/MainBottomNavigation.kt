package doingwell.feature.main.app.bottom

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import doingwell.feature.main.R

@Composable
internal fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    backStackEntry: State<NavBackStackEntry?>,
    onItemClick: (String) -> Unit,
) {
    val currentRoute = backStackEntry.value?.destination?.route ?: ""
    NavigationBar(
        modifier = modifier,
    ) {
        BottomNavigationItem.items.forEach { item ->
            if (item.title == R.string.add) {
                FloatingActionButton({
                    if (currentRoute != item.screenRoute) {
                        onItemClick(item.screenRoute)
                    }
                }) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title)
                    )
                }
            } else {
                NavigationBarItem(
                    selected = currentRoute == item.screenRoute,
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            fontSize = 12.sp
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(item.title)
                        )
                    },
                    onClick = {
                        if (currentRoute != item.screenRoute) {
                            onItemClick(item.screenRoute)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainBottomNavigationPreview() {
    val backStackEntry = remember { mutableStateOf(null) }
    MainBottomNavigation(backStackEntry = backStackEntry, onItemClick = {})
}
