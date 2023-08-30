package com.nibav.commons.compose.extensions

import android.content.Context
import com.nibav.commons.helpers.BaseConfig

val Context.config: BaseConfig get() = BaseConfig.newInstance(applicationContext)
