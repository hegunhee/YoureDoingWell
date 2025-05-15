package com.hegunhee.model.photo

data class AlbumWithPhotos(
    val albumName: String,
    val size: Int,
    val photos: List<String>,
)
