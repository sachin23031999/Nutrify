package com.sachin.nutrify.dev.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.hannesdorfmann.instantiator.instance
import com.sachin.nutrify.dev.model.ChatMessage
import com.sachin.nutrify.dev.model.UserInfo
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat

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

        fun generateDummyUsers(count: Int): MutableList<UserInfo> {
            val list = mutableListOf<UserInfo>()
            for(i in 1..count) {
                val user: UserInfo = instance()
                list.add(user)
            }
            return list
        }

        fun generateDummyMessages(count: Int): MutableList<ChatMessage> {
            val list = mutableListOf<ChatMessage>()
            for(i in 1..count) {
                val msg: ChatMessage = instance()
                list.add(msg)
            }
            return list
        }
        fun showToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        fun currentTmsInMillis() = System.currentTimeMillis().toString()

        fun currentTmsReadable() = SimpleDateFormat()
        fun getLineNumber(): Int {
            return Thread.currentThread().stackTrace[2].lineNumber
        }
    }

}