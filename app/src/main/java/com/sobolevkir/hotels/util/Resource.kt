package com.sobolevkir.hotels.util

import androidx.annotation.StringRes

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(@StringRes val messageResId: Int) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()

    inline fun <R> mapResource(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(messageResId)
        is Loading -> Loading
    }
}