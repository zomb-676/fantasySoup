import org.apache.logging.log4j.LogManager
import org.junit.Test
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL43
import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.math.log
import kotlin.system.measureNanoTime


class ShaderCompileTest {
    /**
     * used to test weather shaders can pass compile
     */
    @Test
    fun testShader() {
        val logger = LogManager.getLogger()
        val systemName = System.getProperty("os.name").also{ logger.info("os:${it}") }!!
        if (listOf("Linux", "Unix").any { it == systemName }) {
            return // system may lack gpu
        }
        GLFW.glfwInit()
        val window = GLFW.glfwCreateWindow(1, 1, "rui", 0, 0)
        logger.info("window : $window")
        GLFW.glfwMakeContextCurrent(window)
        createCapabilities()
        val dir = File("src/main/resources/assets/fantasy_soup/shader")
        for (shader in dir.listFiles()!!) {
            val id = when (shader.extension) {
                "vert" -> GL43.glCreateShader(GL43.GL_VERTEX_SHADER)
                "frag" -> GL43.glCreateShader(GL43.GL_FRAGMENT_SHADER)
                else -> {
                    logger.info("shader: ${shader.name}'s type is unknown")
                    continue
                }
            }
            try {
                GL43.glShaderSource(id, FileReader(shader).use { it.readText() })
            } catch (e: IOException) {
                logger.info("failed to read shader ${shader.name}")
            }
            try {
                measureNanoTime { GL43.glCompileShader(id) }.run { logger.info("shader: ${shader.name} compile success , cost time : $this nanoseconds") }
            } catch (e: Exception) {
                logger.info("failed to compile shader ${shader.name}")
                logger.info(e.cause)
            }
        }
        GLFW.glfwTerminate()

    }
}
