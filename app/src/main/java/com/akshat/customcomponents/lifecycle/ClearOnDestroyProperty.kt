package com.akshat.customcomponents.lifecycle

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ClearOnDestroyProperty<T>(
    private val lifecycleProvider: () -> Lifecycle
) : ReadWriteProperty<Any, T>, DefaultLifecycleObserver {

    @VisibleForTesting
    @field:VisibleForTesting
    internal var field: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>) =
        field ?: throw UninitializedPropertyAccessException("Field not set")

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        field = value
        lifecycleProvider().addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        field = null
    }
}

/**
 * Convenience method for [ClearOnDestroyProperty] for viewBinding <T> scoped to the viewLifeCycleOwner of this
 * [Fragment]
 * */
fun <T, ViewBinding> Fragment.viewBinding(): ClearOnDestroyProperty<T>{
    return ClearOnDestroyProperty { viewLifecycleOwner.lifecycle }
}


/*
fun <T, ViewBinding> Activity.viewBinding(): ClearOnDestroyProperty<T>{
    return ClearOnDestroyProperty { viewLifecycleOwner.lifecycle }
}*/
