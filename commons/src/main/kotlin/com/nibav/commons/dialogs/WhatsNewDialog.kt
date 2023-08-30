package com.nibav.commons.dialogs

import android.app.Activity
import android.view.LayoutInflater
import com.nibav.commons.R
import com.nibav.commons.databinding.DialogWhatsNewBinding
import com.nibav.commons.extensions.getAlertDialogBuilder
import com.nibav.commons.extensions.setupDialogStuff
import com.nibav.commons.models.Release

class WhatsNewDialog(val activity: Activity, val releases: List<Release>) {
    init {
        val view = DialogWhatsNewBinding.inflate(LayoutInflater.from(activity), null, false)
        view.whatsNewContent.text = getNewReleases()

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok, null)
            .apply {
                activity.setupDialogStuff(view.root, this, R.string.whats_new, cancelOnTouchOutside = false)
            }
    }

    private fun getNewReleases(): String {
        val sb = StringBuilder()

        releases.forEach {
            val parts = activity.getString(it.textId).split("\n").map(String::trim)
            parts.forEach {
                sb.append("- $it\n")
            }
        }

        return sb.toString()
    }
}
