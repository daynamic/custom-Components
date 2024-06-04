package com.akshat.customcomponents.util

import android.content.res.Resources
import androidx.annotation.StringRes
import java.text.MessageFormat

internal fun Resources.string(@StringRes resId: Int) = getString(resId)

internal fun Resources.string(@StringRes resId: Int, args: Map<String, Any>) : String{
    val unformatted = getString(resId)
    return MessageFormat.format(unformatted, args)
}

