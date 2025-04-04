package com.hegunhee.daily.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hegunhee.daily.DailyRootScreen
import com.hegunhee.model.user.UserData

const val DAILY_ROUTE = "DAILY"

fun NavController.navigateDaily() {
    navigate(DAILY_ROUTE) {
        popUpTo(graph.id) { inclusive = true }
    }
}

fun NavGraphBuilder.dailyNavGraph(
    paddingValues: PaddingValues,
    userData: UserData?,
    onClickSignOut: () -> Unit,
) {
    composable(DAILY_ROUTE) {
        DailyRootScreen(
            paddingValues = paddingValues,
            userData = userData,
            onClickSignOut = onClickSignOut
        )
    }
}
