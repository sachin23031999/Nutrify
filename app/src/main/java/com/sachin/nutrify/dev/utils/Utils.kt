package com.sachin.nutrify.dev.utils

import android.graphics.Bitmap
import android.util.Base64
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import java.io.ByteArrayOutputStream

class Utils {
companion object {
    fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun navigateTo(activity: FragmentActivity, viewId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(viewId, fragment, tag)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

}