package com.nibav.camera.models

import androidx.camera.core.CameraSelector

data class CameraSelectorImageQualities(
    val camSelector: CameraSelector,
    val qualities: List<MySize>,
)
