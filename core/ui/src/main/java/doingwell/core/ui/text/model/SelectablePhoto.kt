package doingwell.core.ui.text.model

import android.net.Uri

data class SelectablePhoto(
    val photo: Uri,
    val selectCount: Int? = null,
)
