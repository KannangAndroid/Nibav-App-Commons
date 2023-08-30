package com.nibav.commons.extensions

import android.content.Context
import com.nibav.commons.models.FileDirItem

fun FileDirItem.isRecycleBinPath(context: Context): Boolean {
    return path.startsWith(context.recycleBinPath)
}
