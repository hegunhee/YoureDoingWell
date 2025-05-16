package doingwell.core.ui.component.photo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.hegunhee.youredoingwell.ui.theme.Orange
import doingwell.core.ui.model.SelectablePhoto

@Composable
fun SelectablePhoto(
    selectablePhoto: SelectablePhoto,
    onClickPhoto: (SelectablePhoto) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                onClickPhoto(selectablePhoto)
            }
            .border(
                border = if (selectablePhoto.selectCount == null) BorderStroke(
                    1.dp,
                    Color.White
                ) else {
                    BorderStroke(1.dp, Orange)
                }
            )
    ) {
        AsyncImage(
            model = selectablePhoto.photo,
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(32.dp)
                .background(
                    color = if (selectablePhoto.selectCount == null) Color.White else Orange,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selectablePhoto.selectCount != null) {
                Text(
                    text = selectablePhoto.selectCount.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}

@Preview
@Composable
private fun SelectablePhotoPreview() {
    var count by remember { mutableStateOf<Int?>(null) }
    SelectablePhoto(
        SelectablePhoto(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFUAfyVe3Easiycyh3isP9wDQTYuSmGPsPQvLIJdEYvQ_DsFq5Ez2Nh_QjiS3oZ3B8ZPfK9cZQyIStmQMV1lDPLw".toUri(),
            selectCount = count,
        ),
        onClickPhoto = { selectablePhoto ->
            if (selectablePhoto.selectCount == null) {
                count = 1
            } else {
                count = null
            }
        }
    )

}