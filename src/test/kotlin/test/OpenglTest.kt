package test

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.assertNoError
import com.github.zomb_676.fantasySoup.render.graphic.Program
import com.github.zomb_676.fantasySoup.render.graphic.Shader
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexArrayObject
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexAttribute
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
//    val vbo = GL43.glGenVertexArrays()
//    GL43.glBindVertexArray(vbo)

//    val buffer = GL43.glGenBuffers()
//    GL43.glBindBuffer(GL45.GL_ARRAY_BUFFER, buffer)
//    GL43.glBufferData(GL45.GL_ARRAY_BUFFER, pos, GL43.GL_STATIC_DRAW)

//    this way , in vertex shader must use dvec3 (3 double)
//    GL43.glEnableVertexAttribArray(0)//pos
//    GL43.glVertexAttribLPointer(0, 3, GL43.GL_DOUBLE, 5.calculateDoubleSize, 0)
//    GL43.glEnableVertexAttribArray(1)//tex
//    GL43.glVertexAttribLPointer(1, 2, GL43.GL_DOUBLE, 5.calculateDoubleSize, 3.calculateDoubleSize.toLong())

//    this way , in vertex shader must use vec3 (3 float)
//    GL43.glEnableVertexAttribArray(0)//pos
//    GL43.glVertexAttribPointer(0,3,GL43.GL_FLOAT,false,5.calculateFloatSize,0)
//    GL43.glEnableVertexAttribArray(1)//tex
//    GL43.glVertexAttribPointer(1,2,GL43.GL_FLOAT,false,5.calculateFloatSize,3.calculateFloatSize.toLong())

//    this way , in vertex shader must use vec3 (3 float) , thought we specify double here , this method will tell opengl cast data to a float
//    GL43.glEnableVertexAttribArray(0)//pos
//    GL43.glVertexAttribPointer(0,3,GL43.GL_DOUBLE,false,5.calculateDoubleSize,0)
//    GL43.glEnableVertexAttribArray(1)//tex
//    GL43.glVertexAttribPointer(1,2,GL43.GL_DOUBLE,false,5.calculateDoubleSize,3.calculateDoubleSize.toLong())
//    val bindingIndex = 0
//
//    GL43.glEnableVertexAttribArray(0)//pos
//    GL43.glVertexAttribFormat(0,3,GL43.GL_FLOAT,false,0)
//    GL43.glVertexAttribBinding(0,bindingIndex)
//    GL43.glEnableVertexAttribArray(1)//tex
//    GL43.glVertexAttribFormat(1,2,GL43.GL_FLOAT,false,3.calculateFloatSize)
//    GL43.glVertexAttribBinding(1,bindingIndex)
//    GL43.glBindVertexBuffer(bindingIndex,vbo,0,5.calculateFloatSize)

    val vboObject = VertexArrayObject()
        .genVertexArrayObject()
        .bindVertexArrayObject()
    val buffer = GL43.glGenBuffers()
    GL43.glBindBuffer(GL45.GL_ARRAY_BUFFER, buffer)
    GL43.glBufferData(GL45.GL_ARRAY_BUFFER, pos, GL43.GL_STATIC_DRAW)
    vboObject.pushVertexType(VertexAttribute(Constants.VertexDataType.VEC3))
        .pushVertexType(VertexAttribute(Constants.VertexDataType.VEC2))
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