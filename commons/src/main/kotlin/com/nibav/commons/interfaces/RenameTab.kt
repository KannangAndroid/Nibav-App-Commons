package com.nibav.commons.interfaces

import com.nibav.commons.activities.BaseSimpleActivity

interface RenameTab {
    fun initTab(activity: BaseSimpleActivity, paths: ArrayList<String>)

    fun dialogConfirmed(useMediaFileExtension: Boolean, callback: (success: Boolean) -> Unit)
}
