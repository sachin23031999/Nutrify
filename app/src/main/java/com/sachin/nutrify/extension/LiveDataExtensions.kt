/*
 * Copyright (C) 2023 Motorola, Inc.
 * All Rights Reserved.
 *
 * The contents of this file are Motorola Confidential Restricted (MCR).
 */
package com.sachin.nutrify.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Cast MutableLiveData to LiveData instance.
 */
fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

/**
 * Observe the [LiveData] just the first not nullable data, then remove the observer.
 */
fun <T> LiveData<T>.observeOnceNotNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(value: T) {
                if (value != null) {
                    observer.onChanged(value)
                    removeObserver(this)
                }
            }
        }
    )
}
