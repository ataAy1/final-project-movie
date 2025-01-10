package com.finalproject.util

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher

object UtilImage {

    const val PICK_IMAGE_REQUEST = 1001

    fun openImagePicker(launcher: ActivityResultLauncher<Intent>) {
        val pickImageIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
        launcher.launch(pickImageIntent)
    }

    fun handleImagePickerResult(
        data: Intent?,
        selectedImageUris: MutableList<Uri>
    ) {
        data?.clipData?.let { clipData ->
            for (i in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(i).uri
                selectedImageUris.add(uri)
            }
        } ?: run {
            data?.data?.let { uri ->
                selectedImageUris.add(uri)
            }
        }
    }
}
