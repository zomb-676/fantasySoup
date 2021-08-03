package com.github.zomb_676.fantasySoup.utils

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.loading.FMLEnvironment
import java.lang.reflect.Constructor
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
    try {
        constructor = this.getConstructor(* parameters.map { it.javaClass }.toTypedArray())
        return constructor
    } catch (e: NoSuchMethodException) {} catch (e: SecurityException) {}
    try {
        constructor = this.getConstructor()
        return constructor
    } catch (e: NoSuchMethodException) {} catch (e: SecurityException) {}
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "failed to get constructor for class ${this.name}")
    if (parameters.isNotEmpty()) {
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "parameters are as follows")
        parameters.map { it.javaClass.name }.forEach { FantasySoup.logger.fatal(FantasySoup.coreMarker, it) }
    }
    throw RuntimeException("failed to get constructor for class ${this.name} ")

}

fun <T : Any> Class<T>.newInstanceForEmptyOrSpecificConstructor(vararg parameters: Any): T {
    val constructor:Constructor<T> = getEmptyOrSpecificConstructor(parameters)
    try {
        return if (constructor.parameters.isNotEmpty())  constructor.newInstance(parameters) else constructor.newInstance()
    } catch (e: IllegalArgumentException) {
        FantasySoup.logger.fatal(FantasySoup.coreMarker, "wrong number of arguments for class ${this.name}")
        if (parameters.isNotEmpty()){
            FantasySoup.logger.fatal(FantasySoup.coreMarker, "parameters are as follows")
            parameters.map { it.javaClass.name to it }.forEach {
                FantasySoup.logger.fatal(FantasySoup.coreMarker, "${it.second} -> ${it.first}") }
        }
        FantasySoup.logger.fatal(FantasySoup.coreMarker,"constructor parameters are as follows")
        constructor.parameters.map { it.name }.forEach { FantasySoup.logger.fatal(FantasySoup.coreMarker,it) }
        FantasySoup.logger.fatal(FantasySoup.coreMarker,"available constructors are as follows")
        this.declaredConstructors.forEach { _constructor->
            FantasySoup.logger.fatal(FantasySoup.coreMarker,_constructor.parameters.map { it.name })
        }
        throw e
    }
}

fun <T : Any> Class<T>.emptyNewInstance(): T = this.getConstructor().newInstance()

@OptIn(ExperimentalContracts::class)
inline fun<T : Any> T.takeIfOrReturn(predicate:(T)->Boolean,codeBlock: (T) -> Unit):T {
    contract {
        callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
        callsInPlace(codeBlock, InvocationKind.EXACTLY_ONCE)
    }
    if (predicate(this)){
        codeBlock(this)
    }
    return this
}

inline fun runOnClientJar(codeBlock: () -> Unit) {
    if (FMLEnvironment.dist == Dist.CLIENT) codeBlock() else return
}

@OptIn(ExperimentalContracts::class)
inline fun<T : Any> T.takeIfOnClient(codeBlock: (T) -> Unit):T{
    contract {
        callsInPlace(codeBlock, InvocationKind.EXACTLY_ONCE)
    }
    runOnClientJar { codeBlock(this) }
    return this
}