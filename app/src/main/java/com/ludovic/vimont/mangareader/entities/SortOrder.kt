package com.ludovic.vimont.mangareader.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortOrder: Parcelable {
    OLD_TO_RECENT,
    RECENT_TO_OLD
}