package com.bpositive.technician.utils

interface TaskCallback<T> {

    fun onComplete(result: T?)
    fun onException(t: Throwable?)

}