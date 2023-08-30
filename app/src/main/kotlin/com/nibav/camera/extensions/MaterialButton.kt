package com.nibav.camera.extensions

import androidx.annotation.DrawableRes
import com.google.android.material.button.MaterialButton
import com.nibav.camera.R
import com.nibav.camera.views.ShadowDrawable

fun MaterialButton.setShadowIcon(@DrawableRes drawableResId: Int) {
    icon = ShadowDrawable(context, drawableResId, R.style.TopIconShadow)
}
