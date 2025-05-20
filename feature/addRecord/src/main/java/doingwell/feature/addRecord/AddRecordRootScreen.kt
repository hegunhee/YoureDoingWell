package doingwell.feature.addRecord

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.model.user.DateTimeInfo
import com.hegunhee.model.user.UserData
import doingwell.core.common.ObserveAsEvents
import doingwell.core.ui.component.photo.AddSmallPhoto
import doingwell.core.ui.component.photo.SmallPhoto
import doingwell.feature.addRecord.viewmodel.AddRecordUiEvent
import doingwell.feature.addRecord.viewmodel.AddRecordUiEvent.PhotoError
import doingwell.feature.addRecord.viewmodel.AddRecordUiEvent.Save
import doingwell.feature.addRecord.viewmodel.AddRecordUiEvent.TimeOut
import doingwell.feature.addRecord.viewmodel.AddRecordViewModel

@Composable
fun AddRecordRootScreen(
    viewModel: AddRecordViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    userData: UserData?,
    onClickAddPhoto: (maxPhotoCount: Int, currentPhotoCount: Int) -> Unit,
    getAddedPhoto: () -> List<Uri>?,
    onPhotoRemoveSavedStateHandle: () -> Unit,
    onRecordToMain: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(getAddedPhoto()) {
        val addedPhoto = getAddedPhoto()
        if (addedPhoto != null) {
            viewModel.addPhotos(addedPhoto)
            onPhotoRemoveSavedStateHandle()
        }
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            Save -> {
                onRecordToMain()
            }

            PhotoError -> {
                Toast.makeText(context, context.getString(R.string.photo_error), Toast.LENGTH_SHORT)
                    .show()
            }
            TimeOut -> {
                Toast.makeText(context, context.getString(R.string.time_out), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    val (title, onTitleTextChanged) = remember { mutableStateOf("") }
    val (description, onDescriptionChanged) = remember { mutableStateOf("") }
    val photos = viewModel.photos.collectAsStateWithLifecycle().value

    val startedAt = viewModel.startedAt.collectAsStateWithLifecycle().value
    val endedAt = viewModel.endedAt.collectAsStateWithLifecycle().value

    if (userData != null) {
        AddRecordScreen(
            paddingValues = paddingValues,
            userData = userData,
            photos = photos,
            title = title,
            startedAt = startedAt,
            endedAt = endedAt,
            description = description,
            onClickAddPhoto = onClickAddPhoto,
            onclickDeletePhoto = viewModel::removePhoto,
            onTitleTextChanged = onTitleTextChanged,
            onDescriptionTextChanged = onDescriptionChanged,
            onClickAddTime = viewModel::addEndTime,
            onClickReturnTime = viewModel::returnEndedAt,
            onClickSaveButton = viewModel::saveRecord,
        )
    }
}

@Composable
internal fun AddRecordScreen(
    paddingValues: PaddingValues,
    userData: UserData,
    photos: List<String>,
    title: String,
    description: String,
    startedAt: DateTimeInfo,
    endedAt: DateTimeInfo,
    onClickAddPhoto: (maxPhotoCount: Int, currentPhotoCount: Int) -> Unit,
    onclickDeletePhoto: (String) -> Unit,
    onTitleTextChanged: (String) -> Unit,
    onDescriptionTextChanged: (String) -> Unit,
    onClickSaveButton: (title: String, decsription: String, userId: String) -> Unit,
    onClickAddTime: (Int, Int) -> Unit,
    onClickReturnTime: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(10.dp)
    ) {
        Text(
            stringResource(R.string.new_record),
            fontSize = 20.sp
        )
        Text(
            stringResource(R.string.photo),
            fontSize = 15.sp,
        )
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                AddSmallPhoto(
                    photoCount = photos.size,
                    onClickAddPhoto = onClickAddPhoto,
                    onClickOverPhotoClick = {},
                )
            }

            items(photos) { photo ->
                SmallPhoto(
                    photo,
                    onClickPhoto = {},
                    onClickDeletePhoto = onclickDeletePhoto,
                )
            }
        }
        Row(
            modifier = modifier
                .padding(top = 5.dp)
        ) {
            Text(
                text = "제목",
                fontSize = 20.sp
            )
            Text(
                text = "*",
                fontSize = 15.sp,
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = title,
            onValueChange = onTitleTextChanged,
            placeholder = { Text(stringResource(R.string.enter_title)) },
            modifier = modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = modifier
                .padding(top = 5.dp)
        ) {
            Text(
                text = "설명",
                fontSize = 20.sp
            )
        }

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionTextChanged,
            placeholder = { Text(stringResource(R.string.enter_description)) },
            modifier = modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .height(150.dp)
        )

        Row(
            modifier = modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.start_time),
                fontSize = 20.sp,
                modifier = modifier.alignByBaseline()
            )
            Text(
                startedAt.getTimeText(),
                fontSize = 20.sp,
                modifier = modifier.alignByBaseline()
            )
        }

        LazyRow(
            modifier = modifier
                .padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (startedAt != endedAt) {
                item {
                    Button(
                        { onClickReturnTime() }
                    ) {
                        Text("초기화")
                    }
                }

            }
            item {
                Button(
                    { onClickAddTime(0, 10) }
                ) {
                    Text("+10분")
                }
            }
            item {
                Button(
                    { onClickAddTime(0, 15) }
                ) {
                    Text("+15분")
                }
            }
            item {
                Button(
                    { onClickAddTime(0, 30) }
                ) {
                    Text("+30분")
                }
            }
            item {
                Button(
                    { onClickAddTime(1, 0) }
                ) {
                    Text("+1시간")
                }
            }
        }

        Row(
            modifier = modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.end_time),
                fontSize = 20.sp,
                modifier = modifier.alignByBaseline()
            )
            Text(
                endedAt.getTimeText(),
                fontSize = 20.sp,
                modifier = modifier.alignByBaseline()
            )
        }

        Spacer(modifier = modifier.weight(1f))
        Button(
            {
                onClickSaveButton(title, description, userData.uid)
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_record))
        }
    }
}

@Preview
@Composable
private fun AddRecordScreenPreview() {
    AddRecordScreen(
        paddingValues = PaddingValues(),
        userData = UserData(),
        photos = listOf(),
        title = "",
        description = "",
        startedAt = DateTimeInfo(0, 0, 0, 0, 0),
        endedAt = DateTimeInfo(0, 0, 0, 0, 0),
        onClickAddPhoto = { _, _ -> },
        onclickDeletePhoto = {},
        onTitleTextChanged = {},
        onDescriptionTextChanged = {},
        onClickAddTime = { _, _ -> },
        onClickReturnTime = {},
        onClickSaveButton = { _, _, _ -> },
    )
}
