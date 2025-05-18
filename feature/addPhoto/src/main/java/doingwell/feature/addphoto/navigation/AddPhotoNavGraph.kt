package doingwell.feature.addphoto.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import doingwell.feature.addphoto.AddPhotoRootScreen

const val ADD_PHOTO_ROUTE = "ADD_PHOTO_ROUTE/{maxPhotoCount}/{currentPhotoCount}"

fun addPhotoRoute(maxPhotoCount: Int, currentPhotoCount: Int): String {
    return "ADD_PHOTO_ROUTE/$maxPhotoCount/$currentPhotoCount"
}

fun NavController.navigateAddPhoto(maxPhotoCount: Int, currentPhotoCount: Int) {
    navigate(addPhotoRoute(maxPhotoCount, currentPhotoCount))
}

fun NavGraphBuilder.addPhotoNavGraph(
    paddingValues: PaddingValues,
    onClickBackStack: () -> Unit,
    onClickAddPhotos: (ArrayList<Uri>) -> Unit,
) {
    composable(
        route = ADD_PHOTO_ROUTE,
        arguments = listOf(navArgument("maxPhotoCount") {
            type = NavType.IntType
        }, navArgument("currentPhotoCount") {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val maxPhotoCount = navBackStackEntry.arguments?.getInt("maxPhotoCount")
        val currentPhotoCount = navBackStackEntry.arguments?.getInt("currentPhotoCount")

        if (maxPhotoCount == null || currentPhotoCount == null) {
            onClickBackStack()
        } else {
            AddPhotoRootScreen(
                paddingValues = paddingValues,
                maxPhotoCount = maxPhotoCount,
                currentPhotoCount = currentPhotoCount,
                onClickBackStack = onClickBackStack,
                onClickAddPhotos = onClickAddPhotos,
            )
        }
    }
}
