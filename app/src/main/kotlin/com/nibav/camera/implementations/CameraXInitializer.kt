package com.nibav.camera.implementations

import android.net.Uri
import androidx.camera.view.PreviewView
import com.nibav.camera.helpers.CameraErrorHandler
import com.nibav.camera.helpers.MediaOutputHelper
import com.nibav.camera.helpers.MediaSoundHelper
import com.nibav.commons.activities.BaseSimpleActivity

class CameraXInitializer(private val activity: BaseSimpleActivity) {

    fun createCameraXPreview(
        previewView: PreviewView,
        listener: CameraXPreviewListener,
        mediaSoundHelper: MediaSoundHelper,
        outputUri: Uri?,
        isThirdPartyIntent: Boolean,
        initInPhotoMode: Boolean,
        empCode: String,
    ): CameraXPreview {
        val cameraErrorHandler = newCameraErrorHandler()
        val mediaOutputHelper = newMediaOutputHelper(cameraErrorHandler, outputUri, isThirdPartyIntent)
        return CameraXPreview(
            activity,
            previewView,
            mediaSoundHelper,
            mediaOutputHelper,
            cameraErrorHandler,
            listener,
            isThirdPartyIntent = isThirdPartyIntent,
            initInPhotoMode = initInPhotoMode,
            empCode,
        )
    }

    private fun newMediaOutputHelper(
        cameraErrorHandler: CameraErrorHandler,
        outputUri: Uri?,
        isThirdPartyIntent: Boolean,
    ): MediaOutputHelper {
        return MediaOutputHelper(
            activity,
            cameraErrorHandler,
            outputUri,
            isThirdPartyIntent,
        )
    }

    private fun newCameraErrorHandler(): CameraErrorHandler {
        return CameraErrorHandler(activity)
    }
}
