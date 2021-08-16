package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType.FRAGMENT_SHADER
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType.VERTEX
import org.lwjgl.opengl.GL43

abstract class Program(private val vertexShader: Shader, private val fragmentShader: Shader) {

    init {
        checkShaderWithValidType()
        @Suppress("LeakingThis")
        allPrograms.add(this)
    }

    @Throws(IllegalStateException::class)
    private fun checkShaderWithValidType() {
        try {
            if (vertexShader.shaderType != VERTEX)
                throw IllegalStateException("$vertexShader is not a vertex shader")
        } catch (ve: IllegalArgumentException) {
            throw ve
        } finally {
            try {
                if (fragmentShader.shaderType != FRAGMENT_SHADER)
                    throw IllegalStateException("$fragmentShader is not a fragment shader ")
            } catch (fe: IllegalArgumentException) {
                throw fe
            }
        }
    }

    private var programId = -1

    companion object {
        private val allPrograms: MutableList<Program> = mutableListOf()
    }

    @Throws(RuntimeException::class)
    fun linkProgram() {
        programId = GL43.glCreateProgram()
        try {
            vertexShader.attachToProgram(programId)
            fragmentShader.attachToProgram(programId)
        } catch (e: RuntimeException) {
            throw RuntimeException(
                "an error occurred while link program with " +
                        "vertex shader ${vertexShader.shaderName} and fragment shader ${vertexShader.shaderName}", e
            )
        } finally {
            FantasySoup.logger.debug(Canvas.openglMarker, "vertex shaderInfo $vertexShader")
            FantasySoup.logger.debug(Canvas.openglMarker, "fragment shaderInfo $fragmentShader")
        }
        GL43.glLinkProgram(programId)
        FantasySoup.logger.info(
            Canvas.openglMarker, "link vertex ${vertexShader.shaderName} " +
                    "with fragment ${fragmentShader.shaderName}"
        )
    }

    fun deleteProgram() {
        GL43.glDeleteProgram(programId)
        allPrograms.remove(this)
        programId = -1
        //TODO program should hold the reference of shader any more , however , type is nonnull
    }

    fun hasLinked() = programId != -1

    override fun toString(): String = "{vertex shader ${vertexShader.shaderName}," +
            "fragment shader ${vertexShader.shaderName}}"

}