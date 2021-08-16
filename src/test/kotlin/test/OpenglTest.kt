package test

import calculateDoubleSize
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL43
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer

fun main(){
    if (!GLFW.glfwInit()){
        print("failed to init glfw")
        return
    }
    val window = GLFW.glfwCreateWindow(800,450,"OpenglTest",MemoryUtil.NULL,MemoryUtil.NULL)
    GLFW.glfwMakeContextCurrent(window)
    GL.createCapabilities()
    val pos = floatArrayOf(
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

        -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
    ).map { it.toDouble() }.toDoubleArray()

    val indices = intArrayOf(
        0, 1, 2,
        1, 3, 0
    )
    val vbo = GL43.glGenVertexArrays()
    GL43.glBindVertexArray(vbo)

    val buffer = GL43.glGenBuffers()
    GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, buffer)
    GL43.glBufferData(GL43.GL_ARRAY_BUFFER, pos, GL43.GL_STATIC_DRAW)
    GL43.glEnableVertexAttribArray(0)//pos
    GL43.glVertexAttribLPointer(0, 3, GL43.GL_DOUBLE,5.calculateDoubleSize(), 0)
    GL43.glEnableVertexAttribArray(1)//tex
    GL43.glVertexAttribLPointer(1, 2, GL43.GL_DOUBLE, 5.calculateDoubleSize(), 3.calculateDoubleSize().toLong())

    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_S, GL43.GL_REPEAT)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_T, GL43.GL_REPEAT)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, GL43.GL_LINEAR)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, GL43.GL_LINEAR)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, GL43.GL_LINEAR_MIPMAP_LINEAR)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, GL43.GL_LINEAR)

    val imageData = readImage("src/test/resources/texture/malayp.png")
    val texture = GL43.glGenTextures();
    GL43.glBindTexture(GL43.GL_TEXTURE_2D, texture)
    GL43.glTexImage2D(
        GL43.GL_TEXTURE_2D,
        0,
        GL43.GL_RGB,
        imageData.width,
        imageData.height,
        0,
        GL43.GL_RGB,
        GL43.GL_UNSIGNED_BYTE,
        imageData.data
    )
    GL43.glGenerateMipmap(GL43.GL_TEXTURE_2D)

    val program = wrappedCreateProgram("basic", "basic")
    GL43.glUseProgram(program)
    GL43.glUniform1i(2, 0)

    while (!GLFW.glfwWindowShouldClose(window)){
        GL43.glClear(GL43.GL_COLOR_BUFFER_BIT.or(GL43.GL_DEPTH_BUFFER_BIT))
        GL43.glDrawArrays(GL43.GL_TRIANGLES,0,36)
        GLFW.glfwSwapBuffers(window)
        GLFW.glfwPollEvents()
    }

}

@Throws(RuntimeException::class)
fun readImage(path: String, channels: Int = 0): ImageData =
    MemoryStack.stackPush().use {
        STBImage.stbi_set_flip_vertically_on_load(true)
        val w = this.mallocInt(1)
        val h = this.mallocInt(1)
        val channel = this.mallocInt(1)
        val buffer = STBImage.stbi_load(path, w, h, channel, channels)
        ImageData(w.get(), h.get(), buffer!!)
    }

data class ImageData(val width: Int, val height: Int, val data: ByteBuffer) {
    fun release() = STBImage.stbi_image_free(data)
}

@Suppress("ConvertTryFinallyToUseCall")
inline fun <T, C : MemoryStack> C.use(block: C.() -> T): T {
    try {
        return block()
    } finally {
        this.close()
    }
}