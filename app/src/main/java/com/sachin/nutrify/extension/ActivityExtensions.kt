package com.sachin.nutrify.extension

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlin.reflect.KClass

/**
 * Show Toast.
 * @param message the string id of message.
 * @param duration the Toast duration
 */
fun Activity.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(getString(message), duration)
}

/**
 * Show Toast.
 * @param message the string of message.
 * @param duration the Toast duration.
 */
fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Show [DialogFragment].
 * @param dialogFragment the [DialogFragment] to be shown.
 */
fun Activity.showDialogFragment(dialogFragment: DialogFragment) =
    (this as? AppCompatActivity)?.let {
        dialogFragment.show(supportFragmentManager, dialogFragment::class.java.simpleName)
        true
    } ?: false

/**
 * Find a [DialogFragment] in current activity.
 *
 * @param clazz the class of the [DialogFragment]
 */
inline fun <reified T : DialogFragment> AppCompatActivity.findDialogFragment(clazz: KClass<T>): T? =
    supportFragmentManager.findFragmentByTag(clazz.java.simpleName) as? T
