package com.nibav.commons.dialogs

import com.nibav.commons.R
import com.nibav.commons.activities.BaseSimpleActivity
import com.nibav.commons.databinding.DialogChangeViewTypeBinding
import com.nibav.commons.extensions.baseConfig
import com.nibav.commons.extensions.getAlertDialogBuilder
import com.nibav.commons.extensions.setupDialogStuff
import com.nibav.commons.helpers.VIEW_TYPE_GRID
import com.nibav.commons.helpers.VIEW_TYPE_LIST

class ChangeViewTypeDialog(val activity: BaseSimpleActivity, val path: String = "", val callback: () -> Unit) {
    private var view: DialogChangeViewTypeBinding
    private var config = activity.baseConfig

    init {
        view = DialogChangeViewTypeBinding.inflate(activity.layoutInflater, null, false).apply {
            val viewToCheck = when (config.viewType) {
                VIEW_TYPE_GRID -> changeViewTypeDialogRadioGrid.id
                else -> changeViewTypeDialogRadioList.id
            }

            changeViewTypeDialogRadio.check(viewToCheck)
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok) { _, _ -> dialogConfirmed() }
            .setNegativeButton(R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(view.root, this)
            }
    }

    private fun dialogConfirmed() {
        val viewType = if (view.changeViewTypeDialogRadioGrid.isChecked) {
            VIEW_TYPE_GRID
        } else {
            VIEW_TYPE_LIST
        }
        config.viewType = viewType
        callback()
    }
}
