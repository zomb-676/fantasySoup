package com.github.zomb_676.fantasySoup.utils

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.loading.FMLEnvironment
import java.lang.reflect.Constructor
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * init code by get the instance of class
 */
fun <T : Any> KClass<T>.manuallyInitClass() {
    try {
        Class.forName(this::qualifiedName.get())
    } catch (e: ClassNotFoundException) {
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "failed to find class called ${this::class.simpleName}")
        FantasySoup.logger.fatal(FantasySoup.coreMarker, e.stackTraceToString())
        throw e
    } catch (e: NullPointerException) {
        FantasySoup.logger.fatal(
            FantasySoup.coreMarker,
            "failed to load class called ${this::class.simpleName} for a npe"
        )
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "stack trace is as follows")
        FantasySoup.logger.fatal(FantasySoup.coreMarker, e.stackTraceToString())
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "reason is as follows")
        FantasySoup.logger.fatal(FantasySoup.coreMarker, e.message)
    }
}

@Throws(RuntimeException::class)
fun <T : Any> Class<T>.getEmptyOrSpecificConstructor(vararg parameters: Any): Constructor<T> {
    var constructor: Constructor<T>
    //try gets constructor
    try {
        constructor = this.getDeclaredConstructor(* parameters.map { it.javaClass }.toTypedArray())
        return constructor
    } catch (e: NoSuchMethodException) {
    } catch (e: SecurityException) {
    }
    try {
        constructor = this.getDeclaredConstructor()
        return constructor
    } catch (e: NoSuchMethodException) {
    } catch (e: SecurityException) {
    }

    //if no constructor is got , throw an exception

    //log parameters with their value and type
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "failed to get constructor for class ${this.name}")
    if (parameters.isNotEmpty()) {
        logParametersWithTypeAndValue(* parameters)
    }
    logDeclaredConstructors()
    throw RuntimeException("failed to get constructor for class ${this.name} ")

}

fun <T : Any> Class<T>.newInstanceForEmptyOrSpecificConstructor(vararg parameters: Any): T {
    val constructor: Constructor<T> = getEmptyOrSpecificConstructor(* parameters)
    try {
        return if (constructor.parameters.isNotEmpty()) {
            constructor.newInstance(*parameters)
        } else {
            //log some info
            if (parameters.isNotEmpty()) {
                FantasySoup.logger.fatal(
                    FantasySoup.coreMarker,
                    "use empty constructor with none empty parameter"
                )
                logParametersWithTypeAndValue(* parameters)
            }
            constructor.newInstance()
        }
    } catch (e: IllegalArgumentException) {
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "wrong number of arguments for class ${this.name}")
        //log parameters with their type and value
        if (parameters.isNotEmpty()) {
            logParametersWithTypeAndValue(* parameters)
        }
        //log the gotten constructor's parameters info
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "constructor parameters are as follows")
        constructor.parameters.map { it.name }.forEach { FantasySoup.logger.fatal(FantasySoup.coreMarker, it) }
        //log declared constructors
        logDeclaredConstructors()
        throw e
    }
}

private fun <T : Any> Class<T>.logDeclaredConstructors() {
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "available constructors are as follows")
    this.declaredConstructors.forEach { constructor ->
        FantasySoup.logger.fatal(FantasySoup.coreMarker, constructor.parameters.map { it.parameterizedType.typeName })
    }
}

/**
 * log parameters with their type and value
 */
private fun logParametersWithTypeAndValue(vararg parameters: Any) {
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "parameters are as follows , value->type")
    parameters.map { it.javaClass.name to it }.forEach {
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "${it.second} -> ${it.first}")
    }
}

fun <T : Any> Class<T>.emptyNewInstance(): T = this.getConstructor().newInstance()

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> T.takeIfOrReturn(predicate: (T) -> Boolean, codeBlock: (T) -> Unit): T {
    contract {
        callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
        callsInPlace(codeBlock, InvocationKind.EXACTLY_ONCE)
    }
    if (predicate(this)) {
        codeBlock(this)
    }
    return this
}

inline fun runOnClientJar(codeBlock: () -> Unit) {
    if (FMLEnvironment.dist == Dist.CLIENT) codeBlock() else return
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> T.takeIfOnClientJar(codeBlock: (T) -> Unit): T {
    contract {
        callsInPlace(codeBlock, InvocationKind.EXACTLY_ONCE)
    }
    runOnClientJar { codeBlock(this) }
    return this
}

fun modResourcesLocation(path: String) = ResourceLocation(FantasySoup.modId, path)

fun String.rough() = this.lowercase(Locale.getDefault())
    .replace(Regex("_"), "")

@Throws(RuntimeException::class)
fun <T : Any> T?.getOrThrow(errorInfo: String? = null): T {
    if (this != null) return this
    throw RuntimeException(errorInfo)
}

inline fun <T : Any, U> T?.takeIfNull(codeBlock: () -> U) = this ?: codeBlock()
inline fun <T : Any, U> T?.takeIfNotNull(codeBlock: () -> U) = this?.run { codeBlock() }

fun String.math(regex: Regex, startIndex: Int = 0) = regex.find(this, startIndex)

/**
 * wrap [kotlin.run] with try
 */
inline fun <T : Any?> runTry(codeBlock: () -> T): T =
    try {
        run(codeBlock)
    } catch (e: RuntimeException) {
        throw e
    }

/**
 * wrap code bolck with try
 */
inline fun <T : Any> wrapTry(crossinline codeBlock: () -> T): () -> T = { runTry(codeBlock) }