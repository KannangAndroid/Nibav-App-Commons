package com.nibav.commons.dialogs

import com.nibav.commons.R
import com.nibav.commons.activities.BaseSimpleActivity
import com.nibav.commons.databinding.DialogOpenDeviceSettingsBinding
import com.nibav.commons.extensions.getAlertDialogBuilder
import com.nibav.commons.extensions.openDeviceSettings
import com.nibav.commons.extensions.setupDialogStuff

class OpenDeviceSettingsDialog(val activity: BaseSimpleActivity, message: String) {

    init {
        activity.apply {
            val view = DialogOpenDeviceSettingsBinding.inflate(layoutInflater, null, false)
            view.openDeviceSettings.text = message
            getAlertDialogBuilder()
                .setNegativeButton(R.string.close, null)
                .setPositiveButton(R.string.go_to_settings) { _, _ ->
                    openDeviceSettings()
                }.apply {
                    setupDialogStuff(view.root, this)
                }
        }
    }
}
