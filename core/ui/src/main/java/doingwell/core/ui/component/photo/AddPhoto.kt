package doingwell.core.ui.component.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hegunhee.youredoingwell.ui.theme.Orange
import doingwell.core.ui.R

@Composable
fun AddSmallPhoto(
    photoCount: Int,
    onClickAddPhoto: (maxPhotoCount: Int, currentPhotoCount: Int) -> Unit,
    onClickOverPhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.smallPhotoSize))
            .clip(RoundedCornerShape(5))
            .background(Color.Gray)
            .clickable {
                if (photoCount <= 4) {
                    onClickAddPhoto(4, 4 - photoCount)
                } else {
                    onClickOverPhotoClick()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add_photo_24),
            contentDescription = stringResource(
                R.string.add_photo_desc
            )
        )
        val photoCountColor = remember(photoCount) { if (photoCount == 4) Orange else Color.Black }
        Text("($photoCount / 4)", color = photoCountColor)
    }
}

@Preview
@Composable
private fun AddSmallPhotoPreview() {
    AddSmallPhoto(
        photoCount = 4,
        onClickAddPhoto = {_, _ ->},
        onClickOverPhotoClick = {},
    )
}
