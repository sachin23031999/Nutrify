package com.sachin.nutrify.extension

import android.app.Service
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sachin.nutrify.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber.d
import timber.log.Timber.e

fun Fragment.navigate(
    @IdRes
    fragmentDirection: Int,
    args: Bundle? = null,
) {
    findNavController().navigate(fragmentDirection, args)
}

/**
 * Timer of animation.
 */
const val ANIMATION_DELAY = 150L

/**
 * Function extension to call onBackPressed on fragments.
 */
fun Fragment.onBackPressed() = activity?.onBackPressedDispatcher?.onBackPressed()

/**
 * Function extension to finish Activity from fragments.
 */
fun Fragment.finishActivity() {
    activity?.finish()
}

/**
 * Function extension to get color.
 * @param id the id of resource color.
 * @return color resource.
 */
fun Fragment.getColor(@ColorRes id: Int) = resources.getColor(id, activity?.theme)

/**
 * Function extension to get a drawable via ResourcesCompat.
 * @param id the id of resource drawable.
 * @return drawable resource.
 */
fun Fragment.getDrawable(@DrawableRes id: Int) =
    ResourcesCompat.getDrawable(resources, id, activity?.theme)

/**
 * Shows a [Snackbar] with ghe given message.
 * @param message id of string message of the snackBar.
 */
fun Fragment.showSnackBar(@StringRes message: Int) {
    if (activity?.isFinishing == false) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    } else {
        d("showSnackBar - activity has been finished")
    }
}

/**
 * Shows a [Snackbar] with ghe given message.
 * @param message message of the snackBar.
 */
fun Fragment.showSnackBar(message: String) {
    if (activity?.isFinishing == false) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    } else {
        d("showSnackBar - activity has been finished")
    }
}

/**
 * Show Toast.
 * @param messageId the string id of message.
 * @param duration the Toast duration
 */
fun Fragment.showToast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) {
    val message = context?.resources?.getString(messageId) ?: return
    showToast(message, duration)
}

/**
 * Show Toast.
 * @param message the string of message.
 * @param duration the Toast duration.
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    activity?.let { activity -> Toast.makeText(activity, message, duration).show() }
}

/**
 * This function add callback to Android Native Back Button.
 */
fun Fragment.handleOnBackPressed(callBack: () -> Unit): OnBackPressedCallback {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callBack()
        }
    }
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        onBackPressedCallback
    ) ?: e("Cannot add callBack for onBackPressed")

    return onBackPressedCallback
}

/**
 * Run code block when app is running in debug mode.
 * @param action the code be performed.
 */
fun Fragment.runInDebugMode(action: () -> Unit) {
    if (BuildConfig.DEBUG) {
        action()
    }
}

/**
 * This function get Long extra in intent Activity.
 * @param key key of value.
 * @param defaultValue value to return when not found param.
 * @return Long
 */
fun Fragment.getLongExtraIntent(key: String, defaultValue: Long = -1L) =
    activity?.intent?.getLongExtra(key, defaultValue) ?: defaultValue

/**
 * This function return a instance of [InputMethodManager].
 * @return instance of [InputMethodManager] or null.
 */
fun Fragment.getInputMethodManager() =
    activity?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager

/**
 * This function check if keyboard is being displayed.
 * @return Boolean, true if keyboard it's showing or false if it's not showing.
 */
fun Fragment.isKeyboardOpen() = getInputMethodManager()?.isActive ?: false

/**
 * This function close the keyboard.
 */
fun Fragment.closeKeyboard() {
    activity?.currentFocus?.let { view ->
        getInputMethodManager()?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * This function close keyboard and call a callback.
 * If the keyboard is being displayed, it's be closed and after a delay the
 * callback will be invoked.
 * This delay is to wait the keyboard close finish.
 * @param onKeyBoardClosed the call back to be invoked.
 */
fun Fragment.closeKeyboardWithDelay(onKeyBoardClosed: () -> Unit) {
    if (isKeyboardOpen()) {
        lifecycleScope.launch {
            closeKeyboard()
            delay(ANIMATION_DELAY)
            onKeyBoardClosed()
        }
    } else {
        onKeyBoardClosed()
    }
}

/**
 * Determine whether <em>you</em> have been granted a particular permission.
 *
 * @param permission The name of the permission being checked.
 * @return {@link PackageManager#PERMISSION_GRANTED} if you have the
 * permission, or {@link PackageManager#PERMISSION_DENIED} if not.
 * @see PackageManager#checkPermission(String, String)
 */
fun Fragment.hasPermission(permissionId: String) = activity?.let {
    ActivityCompat.checkSelfPermission(it, permissionId) == PackageManager.PERMISSION_GRANTED
} ?: false

/**
 * Finds a fragment that was identified by the given id either when inflated
 * from XML or as the container ID when added in a transaction.  This first
 * searches through fragments that are currently added to the manager's
 * activity; if no such fragment is found, then all fragments currently
 * on the back stack associated with this ID are searched.
 * @return The fragment if found or null otherwise.
 */
fun Fragment.findFragmentById(@IdRes id: Int) = childFragmentManager.findFragmentById(id)
