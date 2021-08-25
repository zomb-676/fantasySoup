package test

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.addGlDebugMessageCallback
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
import java.lang.management.ManagementFactory

fun main() {
//    System.loadLibrary("renderdoc")
    if (!GLFW.glfwInit()) {
        print("failed to init glfw")
        return
    }
    val width = 800;
    val height = 450;
    val window = GLFW.glfwCreateWindow(width, height, "OpenglTest", MemoryUtil.NULL, MemoryUtil.NULL)
    GLFW.glfwMakeContextCurrent(window)
    GL.createCapabilities()
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT,GL43.GL_FALSE)
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_COMPAT_PROFILE,GL43.GL_FALSE)
    assertNoError()
    println(ManagementFactory.getRuntimeMXBean().name.split("@")[0])
    GL43.glEnable(GL43.GL_DEBUG_OUTPUT)
    assertNoError()
    addGlDebugMessageCallback(1)
    assertNoError()
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3)
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL43.GL_FALSE)
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)

    println(GLFW.glfwGetVersionString())
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

    val pos2 = floatArrayOf(
        -1.0f, -1.0f, 0.0f,     -1.0f, -1.0f,
        1.0f, -1.0f, 0.0f,      1.0f, -1.0f,
        1.0f, 1.0f, 0.0f,       1.0f, 1.0f,

        -1.0f, -1.0f, 0.0f,     -1.0f, -1.0f,
        1.0f, 1.0f, 0.0f,       1.0f, 1.0f,
        -1.0f, 1.0f, 0.0f,      -1.0f, 1.0f,
    )

    val posTest = floatArrayOf(
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        1.0f,1.0f, 0.0f,

        -1.0f, -1.0f, 0.0f,
        1.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f
    )

    val posAttribute = VertexAttribute(Constants.VertexDataType.VEC3, "pos")
    val texAttribute = VertexAttribute(Constants.VertexDataType.VEC2, "tex")

    val vaoBlur = VertexArrayObject()
        .genVertexArrayObject()
        .bindVertexArrayObject()

    val vboBlur = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
        .genVertexBufferObject()
        .bindVertexBufferObject()
        .bindDate(pos)

    vaoBlur.pushVertexType(posAttribute)
        .pushVertexType(texAttribute)
        .setupByAttributePointer()

    val vaoFull = VertexArrayObject()
        .genVertexArrayObject()
        .bindVertexArrayObject()

    val vboFull = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
        .genVertexBufferObject()
        .bindVertexBufferObject()
        .bindDate(pos2)

    vaoFull.pushVertexType(posAttribute)
        .pushVertexType(texAttribute)
        .setupByAttributePointer()

    val vaoTest: VertexArrayObject = VertexArrayObject()
        .genVertexArrayObject()
        .bindVertexArrayObject()
    val vboTest: VertexBufferObject = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
        .genVertexBufferObject()
        .bindVertexBufferObject()
        .bindDate(posTest)

    vaoTest
        .pushVertexType(posAttribute)
//        .pushVertexType(VertexAttribute(Constants.VertexDataType.VEC3,"pos"))
        .setupByAttributePointer()

    val texture: Texture = FileTexture("src/test/resources/texture/malayp.png")
        .genTexture().bindTexture()

    val programDraw = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/main/resources/assets/fantasy_soup/shader/vertex/basic.vsh")),
        Shader(Constants.ShaderType.FRAGMENT, File("src/main/resources/assets/fantasy_soup/shader/fragment/basic.fsh"))
    ).linkProgram()
    val programBlur = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/main/resources/assets/fantasy_soup/shader/vertex/tex_pos.vsh")),
        Shader(Constants.ShaderType.FRAGMENT, File("src/main/resources/assets/fantasy_soup/shader/fragment/blur.fsh"))
    ).linkProgram()
    val programTest = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/main/resources/assets/fantasy_soup/shader/vertex/rectangle.vsh")),
        Shader(Constants.ShaderType.FRAGMENT, File("src/main/resources/assets/fantasy_soup/shader/fragment/rectangle.fsh"))
    ).linkProgram()

    val frameBuffer = GL43.glGenFramebuffers()
    GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBuffer)
    val frameBufferTex = GL43.glGenTextures()
    GL43.glActiveTexture(GL43.GL_TEXTURE1)
    GL43.glBindTexture(GL43.GL_TEXTURE_2D, frameBufferTex)
    GL43.glTexImage2D(
        GL43.GL_TEXTURE_2D, 0, GL43.GL_RGB, width, height,
        0, GL43.GL_RGB, GL43.GL_UNSIGNED_BYTE, MemoryUtil.NULL
    )
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, GL43.GL_LINEAR)
    GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, GL43.GL_LINEAR)
    GL43.glFramebufferTexture2D(
        GL43.GL_FRAMEBUFFER, GL43.GL_COLOR_ATTACHMENT0,
        GL43.GL_TEXTURE_2D, frameBufferTex, 0
    )

//    var rad = 0.1f
    var rad = 2f
    var isAdd = 1
    GL43.glViewport(0,0,width,height)
    while (!GLFW.glfwWindowShouldClose(window)) {
        GL43.glClear(GL45.GL_COLOR_BUFFER_BIT.or(GL45.GL_DEPTH_BUFFER_BIT))

//        vaoFull.bindVertexArrayObject()
//        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBuffer)
//        programDraw.useProgram()
//        GL43.glUniform1i(1, 0)
//        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 6)

//        vaoBlur.bindVertexArrayObject()
//        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, 0)
//        programDraw.useProgram()
//        GL43.glUniform1i(1, 1)
//        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 36)

//        if (rad<=0f){
//            isAdd = 1
//        }else if (rad >= 5f){
//            isAdd = -1
//        }
//        rad += (0.1 * isAdd).toFloat()

//        vaoBlur.bindVertexArrayObject()
//        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, 0)
//        programBlur.useProgram()
//        GL43.glUniform2f(0, 5f, 5f)
//        GL43.glUniform1f(1, rad)
//        GL43.glUniform1i(2, 1)
//        GL43.glUniform2f(3, 1f / width, 1f / height)
//        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 36)

        vaoTest.bindVertexArrayObject()
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER,0)
        programTest.useProgram()
        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 6)

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