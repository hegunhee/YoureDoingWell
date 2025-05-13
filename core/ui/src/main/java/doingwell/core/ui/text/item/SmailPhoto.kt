package doingwell.core.ui.text.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import doingwell.core.ui.R

@Composable
fun SmallPhoto(
    url: String,
    onClickPhoto: (String) -> Unit,
    onClickDeletePhoto: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.smallPhotoSize))
            .clip(RoundedCornerShape(5))
            .background(Color.Gray)
            .clickable {
                onClickPhoto(url)
            }
    ) {
        IconButton(
            onClick = { onClickDeletePhoto(url) },
            modifier = modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.delete_photo),
                tint = Color.Red
            )
        }
    }
}

@Preview
@Composable
private fun FillSmallPhoto() {
    val url = ""
    SmallPhoto(
        url = url,
        onClickPhoto = {},
        onClickDeletePhoto = {},
        modifier = Modifier,
    )
}
