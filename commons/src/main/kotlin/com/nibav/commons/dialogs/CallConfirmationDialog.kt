package com.nibav.commons.dialogs

import android.view.animation.AnimationUtils
import com.nibav.commons.R
import com.nibav.commons.activities.BaseSimpleActivity
import com.nibav.commons.databinding.DialogCallConfirmationBinding
import com.nibav.commons.extensions.applyColorFilter
import com.nibav.commons.extensions.getAlertDialogBuilder
import com.nibav.commons.extensions.getProperTextColor
import com.nibav.commons.extensions.setupDialogStuff

class CallConfirmationDialog(val activity: BaseSimpleActivity, val callee: String, private val callback: () -> Unit) {
    private var view = DialogCallConfirmationBinding.inflate(activity.layoutInflater, null, false)

    init {
        view.callConfirmPhone.applyColorFilter(activity.getProperTextColor())
        activity.getAlertDialogBuilder()
            .setNegativeButton(R.string.cancel, null)
            .apply {
                val title = String.format(activity.getString(R.string.confirm_calling_person), callee)
                activity.setupDialogStuff(view.root, this, titleText = title) { alertDialog ->
                    view.callConfirmPhone.apply {
                        startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_pulse_animation))
                        setOnClickListener {
                            callback.invoke()
                            alertDialog.dismiss()
                        }
                    }
                }
            }
    }
}
