package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType
import com.github.zomb_676.fantasySoup.utils.getOrThrow
import org.lwjgl.opengl.GL43
import java.io.File
import kotlin.system.measureNanoTime


abstract class  Shader(
    var shaderType: ShaderType,
    private val shaderString: String,
    val shaderName: String
) {
    companion object {
        /**
         * regex:(?!\.)[a-z]+$
         * math file extension exclude .
         */
        val regex = Regex("(?!\\.)[a-z]+\$")
        private val allShaders: MutableList<Shader> = mutableListOf()
    }

    init {
        @Suppress("LeakingThis")
        allShaders.add(this)
    }

    private var shaderId: Int = -1
    private var attachedNum = 0

    constructor(shaderType: ShaderType, shaderPath: File) : this(shaderType, shaderPath.readText(), shaderPath.name)

    @Throws(RuntimeException::class)
    constructor(shaderPath: String) : this(
        ShaderType.getShaderTypeFromExtension(
            regex.find(shaderPath, 0)
                .getOrThrow("can't resolve file extension string from $shaderPath").value
        ), File(shaderPath)
    )

    @Throws(RuntimeException::class)
    fun compileShader() {
        val shaderId = GL43.glCreateShader(shaderType.type)
        GL43.glShaderSource(shaderId, shaderString)
        measureNanoTime { GL43.glCompileShader(shaderId) }
            .run {
                FantasySoup.logger.info(
                    Canvas.openglMarker,
                    "compile shader: $shaderName  success , cost time : $this nanoseconds"
                )
            }
        GL43.glGetShaderInfoLog(shaderId).takeIf { it.isNotEmpty() }?.let  {
            println("shader status:$it")
            throw RuntimeException("failed to compile shader , reason $it")
        }
    }

    fun deleteShader() {
        GL43.glDeleteShader(shaderId)
        allShaders.remove(this)
        shaderId = -1
        attachedNum--
    }

    fun attachToProgram(programID:Int){
        if (!isCompiled())
            compileShader()
        GL43.glAttachShader(programID,shaderId)
        attachedNum++
    }

    fun isCompiled() = shaderId!=-1

    override fun toString(): String ="{type:${shaderType.typeName},name:$shaderName}"
}