package com.github.zomb_676.fantasySoup.capability

import net.minecraftforge.common.capabilities.Capability.IStorage
import java.util.concurrent.Callable
import kotlin.reflect.KClass


interface ICapability<T : Any> {
    fun storageGuide(): IStorage<T>
    fun factory(): Callable<T>
//    fun getContextClass() : KClass<T>
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class InnerClass(val value: KClass<*>)