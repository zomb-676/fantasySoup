import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL43
import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * used to test weather shaders can pass compile
 */
fun main() {
    GLFW.glfwInit()
    val window = GLFW.glfwCreateWindow(1,1,"rui",0,0)
    println("window : $window")
    GLFW.glfwMakeContextCurrent(window)
    createCapabilities()
    val dir = File("src/main/resources/assets/fantasy_soup/shader")
    for (shader in dir.listFiles()!!){
        val id =when(shader.extension){
            "vert"->GL43.glCreateShader(GL43.GL_VERTEX_SHADER)
            "frag"->GL43.glCreateShader(GL43.GL_FRAGMENT_SHADER)
            else -> {
                println("shader: ${shader.name}'s type is unknown")
                continue
            }
        }
        try {
            GL43.glShaderSource(id,FileReader(shader).use { it.readText() })
        }catch (e:IOException){
            println("failed to read shader ${shader.name}")
        }
        try {
            measureNanoTime{ GL43.glCompileShader(id) }.run { println("shader: ${shader.name} compile success , cost time : $this nanoseconds") }
        }catch (e : Exception){
            println("failed to compile shader ${shader.name}")
            println(e.cause)
        }
    }
    GLFW.glfwTerminate()
}