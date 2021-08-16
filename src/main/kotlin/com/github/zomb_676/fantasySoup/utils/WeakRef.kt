package com.github.zomb_676.fantasySoup.utils

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

//class WeakRef<T:Any,V:Any?>(v:V) : ReadWriteProperty<T,V> {
//    private var weakReference: WeakReference<V> = WeakReference(v)
//    override fun getValue(thisRef: T, property: KProperty<*>): V = weakReference.get()
//
//    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
//        weakReference = WeakReference(value)
//    }
//}

class WeakRef<V : Any>(v: V) {
    private var weakReference: WeakReference<V> = WeakReference(v)
    @Throws(RuntimeException::class)
    operator fun getValue(thisRef: Any?, property: KProperty<*>): V {
        return weakReference.get() ?: throw RuntimeException("try to access a removed reference")
    }


    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        weakReference = WeakReference(value)
    }
}

