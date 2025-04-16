package doingwell.core.ui.text.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import doingwell.core.ui.R

@Composable
fun SmallPhoto(
    url: String?,
    onClickEmptyPhoto: () -> Unit,
    onClickDeletePhoto: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(RoundedCornerShape(5))
            .background(Color.Gray)
            .clickable {
                if (url == null) {
                    onClickEmptyPhoto()
                }
            }
    ) {
        if(url == null) {
            Icon(
                imageVector = Icons.Sharp.Add,
                contentDescription = stringResource(R.string.add_photo),
                tint = Color.White,
                modifier = modifier.align(Alignment.Center)
            )
        } else {
            IconButton(
                onClick = { onClickDeletePhoto(url)},
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
}

@Preview
@Composable
private fun EmptySmallPhoto() {
    SmallPhoto(
        url = null,
        onClickEmptyPhoto = {},
        onClickDeletePhoto = {},
        modifier = Modifier,
    )
}

@Preview
@Composable
private fun FillSmallPhoto() {
    val url = ""
    SmallPhoto(
        url = url,
        onClickEmptyPhoto = {},
        onClickDeletePhoto = {},
        modifier = Modifier,
    )
}
