package com.nibav.camera.models

import androidx.annotation.StringRes
import com.nibav.camera.R

enum class CaptureMode(@StringRes val stringResId: Int) {
    MINIMIZE_LATENCY(R.string.minimize_latency),
    MAXIMIZE_QUALITY(R.string.maximize_quality)
}
