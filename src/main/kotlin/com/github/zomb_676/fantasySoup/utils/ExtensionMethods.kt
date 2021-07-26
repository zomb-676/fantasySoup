package com.github.zomb_676.fantasySoup.utils

import com.github.zomb_676.fantasySoup.FantasySoup
import java.lang.reflect.Constructor
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
fun < T : Any> Class<T>.getEmptyOrSpecificConstructor(vararg parameters: Any): Constructor<T> {
    var constructor: Constructor<T>
    try {
        constructor = this.getConstructor()
        return constructor
    } catch (e: NoSuchMethodException) {

    } catch (e: SecurityException) {

    }
    try {
        constructor = this.getConstructor(* parameters.map { it.javaClass }.toTypedArray())
        return constructor
    } catch (e: NoSuchMethodException) {

    } catch (e: SecurityException) {

    }
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "failed to get constructor for class ${this.name}")
    FantasySoup.logger.fatal(FantasySoup.coreMarker, "parameters are as follows")
    classes.map { it.name }.joinToString { "\n" }.forEach { FantasySoup.logger.fatal(FantasySoup.coreMarker, it) }
    throw RuntimeException("failed to get constructor for class ${this.name} ")

}

fun <T : Any> Class<T>.newInstanceForEmptyOrSpecificConstructor(vararg parameters: Any): T =
    getEmptyOrSpecificConstructor(parameters).newInstance(parameters)

fun <T:Any> Class<T>.emptyNewInstance():T=this.getConstructor().newInstance()