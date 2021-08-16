package com.github.zomb_676.fantasySoup.render.graphic

import org.lwjgl.BufferUtils
import java.nio.*


object MemoryFunctions {

    inline val Int.size: Int get() = Int.SIZE_BITS
    inline val Float.size: Int get() = Float.SIZE_BYTES
    inline val Double.size: Int get() = Double.SIZE_BYTES
    inline val Long.size: Int get() = Long.SIZE_BYTES
    inline val Short.size: Int get() = Short.SIZE_BITS

    inline val Int.calculateIntSize: Int get() = Int.SIZE_BYTES * this
    inline val Int.calculateFloatSize: Int get() = Float.SIZE_BYTES * this
    inline val Int.calculateDoubleSize: Int get() = Double.SIZE_BYTES * this
    inline val Int.calculateLongSize: Int get() = Long.SIZE_BYTES * this
    inline val Int.calculateShortSize: Int get() = Short.SIZE_BYTES * this

    fun DoubleArray.wrapper(): DoubleBuffer = BufferUtils.createDoubleBuffer(this.size).put(this).flip()
    fun IntArray.wrapper(): IntBuffer = BufferUtils.createIntBuffer(this.size).put(this).flip()
    fun LongArray.wrapper(): LongBuffer = BufferUtils.createLongBuffer(this.size).put(this).flip()
    fun ShortArray.wrapper(): ShortBuffer = BufferUtils.createShortBuffer(this.size).put(this).flip()
    fun CharArray.wrapper(): CharBuffer = BufferUtils.createCharBuffer(this.size).put(this).flip()

}