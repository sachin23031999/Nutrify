package com.sachin.nutrify.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(
    @IdRes
    fragmentDirection: Int,
    args: Bundle? = null,
) {
    findNavController().navigate(fragmentDirection, args)
}
