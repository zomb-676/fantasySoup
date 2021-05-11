package com.github.zomb_676.fantasySoup

import com.github.zomb_676.fantasySoup.dataGen.IDataHandle
import net.minecraft.item.Item
import net.minecraft.nbt.*
import net.minecraft.state.EnumProperty
import net.minecraft.state.Property
import net.minecraft.util.IStringSerializable
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.IForgeRegistryEntry
import kotlin.reflect.KClass

/**
 * like optional.getOrElse
 */
inline fun <T> T?.safeReturn(block: () -> T): T {
    return this ?: block()
}

/**
 * without need for jvm args to enable assert feature
 */
inline fun <T : Any> T?.assert(block: (T?) -> Boolean) {
    if (this == null) {
        throw NullPointerException("try to use assert on a null")
    }
    if (!block(this)) {
        throw RuntimeException("Assert Error")
    }
}

/**
 * implement boolean array by write byte/short/int/long
 */
fun CompoundNBT.putBooleanArrayToSingle(key: String, value: BooleanArray) {
    when {
        value.size <= 8 -> {
            var result: Byte = 0
            value.indices.forEach { index -> value[index].takeIf { it }.let { result =
                result.plus(1.shl(value.size - index)-1).toByte()
            }}
            this.putByte(key,result)
        }
        value.size <= 16 -> {
            var result: Short = 0
            value.indices.forEach { index -> value[index].takeIf { it }.let { result =
                result.plus(1.shl(value.size - index)-1).toShort()
            }}
            this.putShort(key,result)
        }
        value.size <= 32 -> {
            var result: Int = 0
            value.indices.forEach { index -> value[index].takeIf { it }.let { result =
                result.plus(1.shl(value.size - index)-1)
            }}
            this.putInt(key,result)
        }
        value.size < 64 -> {
            var result: Long = 0
            value.indices.forEach { index -> value[index].takeIf { it }.let { result =
                result.plus(1.shl(value.size - index)-1)
            }}
            this.putLong(key,result)
        }
        else -> {
            throw RuntimeException(" the booleanArray need to process is longer than 64")
        }
    }
}

/**
 * read boolean array
 */
fun CompoundNBT.getBooleanArray(key : String) : BooleanArray{
    val nbt = get(key) ?: throw RuntimeException("nbt $this didn't has a key called $key")
    val number = (nbt as NumberNBT).asNumber.toLong()
    if (number==0.toLong()){return booleanArrayOf(false)}
    (1..64).forEach { num ->
        if (number.shr(num)==0.toLong()){
            val result = BooleanArray(num)
            (0 until num).forEach{
                if (number.shr(it) %2 == 1L){
                    result[it] = true
                }
            }
            return result
        }
    }
    throw RuntimeException("error")
}

/**
 * init code by get the instance of class
 */
fun <T : Any> KClass<T>.manuallyInitClass(){
    try {
        Class.forName(this::qualifiedName.get())
    }catch (e : ClassNotFoundException){
        FantasySoup.logger.fatal("failed to find class called ${this::class.simpleName}")
    }
}

/**
 * unsafe use , use it if you make sure it is safe
 */
fun <T> LazyOptional<T>.unsafeGet(): T = this.orElseThrow { Exception("try to get the value of a null lazyOptional , $this ") }

inline fun <K, reified V> Map<K, EnumProperty<V>>.toProperties(): List<Property<V>> where V : Comparable<V>, V : Enum<V>, V : IStringSerializable =
    keys.map { EnumProperty.create(it.toString(), V::class.java, get(it)!!.allowedValues) }
