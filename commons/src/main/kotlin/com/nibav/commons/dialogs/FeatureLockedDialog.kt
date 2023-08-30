package com.nibav.commons.dialogs

import android.app.Activity
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AlertDialog
import com.nibav.commons.R
import com.nibav.commons.databinding.DialogFeatureLockedBinding
import com.nibav.commons.extensions.*

class FeatureLockedDialog(val activity: Activity, val callback: () -> Unit) {
    private var dialog: AlertDialog? = null

    init {
        val view = DialogFeatureLockedBinding.inflate(activity.layoutInflater, null, false)
        view.featureLockedImage.applyColorFilter(activity.getProperTextColor())

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.purchase, null)
            .setNegativeButton(R.string.later) { _, _ -> dismissDialog() }
            .setOnDismissListener { dismissDialog() }
            .apply {
                activity.setupDialogStuff(view.root, this, cancelOnTouchOutside = false) { alertDialog ->
                    dialog = alertDialog
                    view.featureLockedDescription.text = Html.fromHtml(activity.getString(R.string.features_locked))
                    view.featureLockedDescription.movementMethod = LinkMovementMethod.getInstance()

                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        activity.launchPurchaseThankYouIntent()
                    }
                }
            }
    }

    fun dismissDialog() {
        dialog?.dismiss()
        callback()
    }
}
