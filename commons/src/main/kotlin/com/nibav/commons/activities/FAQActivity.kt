package com.nibav.commons.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.nibav.commons.compose.extensions.AdjustNavigationBarColors
import com.nibav.commons.compose.extensions.TransparentSystemBars
import com.nibav.commons.compose.screens.FAQScreen
import com.nibav.commons.compose.theme.AppThemeSurface
import com.nibav.commons.helpers.APP_FAQ
import com.nibav.commons.models.FAQItem
import kotlinx.collections.immutable.toImmutableList

class FAQActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TransparentSystemBars()
            AppThemeSurface {
                var canScroll by remember { mutableStateOf<Boolean?>(null) }
                AdjustNavigationBarColors(canScroll)
                val faqItems = remember { intent.getSerializableExtra(APP_FAQ) as ArrayList<FAQItem> }
                FAQScreen(
                    goBack = ::finish,
                    faqItems = faqItems.toImmutableList(),
                    canScroll = { canPerformScroll -> canScroll = canPerformScroll }
                )
            }
        }
    }
}
