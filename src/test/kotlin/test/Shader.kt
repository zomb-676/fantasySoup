package test

import org.lwjgl.opengl.GL43
import java.io.FileReader
import kotlin.system.measureNanoTime


fun wrappedCreateProgram(vertexShader: String, fragmentShader: String): Int =
    createProgram("src/test/resources/vertex/$vertexShader.vsh", "src/test/resources/fragment/$fragmentShader.fsh")

/**
 * @param vertexShader path
 * @param fragmentShader path
 */
fun createProgram(vertexShader: String, fragmentShader: String): Int {
    val vertexId = compileShader(vertexShader, ShaderType.VERTEX)
    val fragmentId = compileShader(fragmentShader, ShaderType.FRAGMENT)
    val programId = GL43.glCreateProgram()
    GL43.glAttachShader(programId, vertexId)
    GL43.glAttachShader(programId, fragmentId)
    GL43.glLinkProgram(programId)
    return programId
}

@Throws(RuntimeException::class)
fun compileShader(path: String, type: ShaderType): Int {
    val text = FileReader(path).use { it.readText() }
    val shaderId: Int = GL43.glCreateShader(type.shaderType)
    GL43.glShaderSource(shaderId, text)
    println("compile cost：${measureNanoTime { GL43.glCompileShader(shaderId) }} nanoseconds")
    GL43.glGetShaderInfoLog(shaderId).takeIf { it.isNotEmpty() }?.let { println("shader status:$it") }
    return shaderId
}

enum class ShaderType(val shaderType: Int) {
    FRAGMENT(GL43.GL_FRAGMENT_SHADER), VERTEX(GL43.GL_VERTEX_SHADER)
}