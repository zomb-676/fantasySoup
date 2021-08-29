package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType.FRAGMENT
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType.VERTEX
import org.lwjgl.opengl.GL43

class Program(private val vertexShader: Shader, private val fragmentShader: Shader , val programName:String) {

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
                if (fragmentShader.shaderType != FRAGMENT)
                    throw IllegalStateException("$fragmentShader is not a fragment shader ")
            } catch (fe: IllegalArgumentException) {
                throw fe
            }
        }
    }

    /**private**/ var programId = -1

    companion object {
        internal val allPrograms: MutableList<Program> = mutableListOf()
    }

    @Throws(RuntimeException::class)
    fun linkProgram(): Program {
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
            FantasySoup.logger.debug(Canvas.graphicMarker, "vertex shaderInfo $vertexShader")
            FantasySoup.logger.debug(Canvas.graphicMarker, "fragment shaderInfo $fragmentShader")
        }
        GL43.glLinkProgram(programId)
        FantasySoup.logger.info(
            Canvas.graphicMarker, "link vertex ${vertexShader.shaderName} " +
                    "with fragment ${fragmentShader.shaderName}"
        )
        return this
    }

    fun deleteProgram() {
        GL43.glDeleteProgram(programId)
        allPrograms.remove(this)
        programId = -1
        //TODO program should hold the reference of shader any more , however , type is nonnull
    }

    fun deleteProgramAndShader() {
        vertexShader.deleteShader()
        fragmentShader.deleteShader()
        GL43.glDeleteProgram(programId)
        allPrograms.remove(this)
        programId = -1
        //TODO program should hold the reference of shader any more , however , type is nonnull
    }

    @Throws(RuntimeException::class)
    fun useProgram(): Program {
        if (programId==-1){
            throw RuntimeException("try to use an invalid program")
        }
        GL43.glUseProgram(programId)
        return this
    }

    fun hasLinked() = programId != -1

    override fun toString(): String = "{vertex shader ${vertexShader.shaderName}," +
            "fragment shader ${vertexShader.shaderName}}"

    fun reloadProgram(){
        this.deleteProgram()
        vertexShader.reloadShader()
        fragmentShader.reloadShader()
        allPrograms.add(this)
        this.linkProgram()
    }
}