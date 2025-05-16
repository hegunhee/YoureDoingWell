package doingwell.core.ui.model

import android.net.Uri

data class SelectablePhoto(
    val photo: Uri,
    val selectCount: Int? = null,
)
