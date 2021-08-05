import org.lwjgl.BufferUtils
import java.nio.*
import javax.naming.OperationNotSupportedException

@Throws(OperationNotSupportedException::class)
fun Number.size(): Int =
    when (this) {
        is Int -> Int.SIZE_BYTES
        is Float -> Float.SIZE_BYTES
        is Double -> Double.SIZE_BYTES
        is Long -> Long.SIZE_BYTES
        is Short -> Short.SIZE_BITS
        else -> throw OperationNotSupportedException("don't support value:$this type:${this.javaClass.simpleName}")
    } * this.toInt()


fun Int.calculateIntSize(): Int = Int.SIZE_BYTES * this
fun Int.calculateFloatSize(): Int = Float.SIZE_BYTES * this
fun Int.calculateDoubleSize(): Int = Double.SIZE_BYTES * this
fun Int.calculateLongSize(): Int = Long.SIZE_BYTES * this
fun Int.calculateShortSize(): Int = Short.SIZE_BYTES * this


fun DoubleArray.wrapper(): DoubleBuffer = BufferUtils.createDoubleBuffer(this.size).put(this).flip()
fun IntArray.wrapper(): IntBuffer = BufferUtils.createIntBuffer(this.size).put(this).flip()
fun LongArray.wrapper(): LongBuffer = BufferUtils.createLongBuffer(this.size).put(this).flip()
fun ShortArray.wrapper(): ShortBuffer = BufferUtils.createShortBuffer(this.size).put(this).flip()
fun CharArray.wrapper(): CharBuffer = BufferUtils.createCharBuffer(this.size).put(this).flip()
