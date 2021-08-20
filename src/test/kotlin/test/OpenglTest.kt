package test

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.assertNoError
import com.github.zomb_676.fantasySoup.render.graphic.Program
import com.github.zomb_676.fantasySoup.render.graphic.Shader
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexArrayObject
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexAttribute
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexBufferObject
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL43
import org.lwjgl.opengl.GL45
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.io.File

fun main() {
    if (!GLFW.glfwInit()) {
        print("failed to init glfw")
        return
    }
    val window = GLFW.glfwCreateWindow(800, 450, "OpenglTest", MemoryUtil.NULL, MemoryUtil.NULL)
    GLFW.glfwMakeContextCurrent(window)
    GL.createCapabilities()
    assertNoError()
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 5)
    assertNoError()
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
    )
//        .map { it.toDouble() }.toDoubleArray()

    val indices = intArrayOf(
        0, 1, 2,
        1, 3, 0
    )

    val posAttribute = VertexAttribute(Constants.VertexDataType.VEC3 , "pos")
    val colorAttribute = VertexAttribute(Constants.VertexDataType.VEC2 , "color")

    val vao = VertexArrayObject()
        .genVertexArrayObject()
        .bindVertexArrayObject()

    val vbo = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
        .genVertexBufferObject()
        .bindVertexBufferObject()
        .bindDate(pos)
    
    vao.pushVertexType(posAttribute)
        .pushVertexType(colorAttribute)
        .setup()
        .bindVertexArrayObject()

    val texture: Texture = FileTexture("src/test/resources/texture/malayp.png")
        .genTexture().bindTexture()

    val program = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/test/resources/vertex/basic.vsh")),
        Shader(Constants.ShaderType.FRAGMENT_SHADER, File("src/test/resources/fragment/basic.fsh"))
    )
        .linkProgram()
        .useProgram()

    GL43.glUniform1i(1, 0)

    while (!GLFW.glfwWindowShouldClose(window)) {
        assertNoError()
        GL43.glClear(GL45.GL_COLOR_BUFFER_BIT.or(GL45.GL_DEPTH_BUFFER_BIT))
        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 36)
        GLFW.glfwSwapBuffers(window)
        GLFW.glfwPollEvents()
    }

}

@Suppress("ConvertTryFinallyToUseCall")
inline fun <T, C : MemoryStack> C.use(block: C.() -> T): T {
    try {
        return block()
    } finally {
        this.close()
    }
}