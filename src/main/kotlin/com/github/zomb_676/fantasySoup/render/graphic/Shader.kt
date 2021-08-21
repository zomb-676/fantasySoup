package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Constants.Regexes.fileExtensionRegex
import com.github.zomb_676.fantasySoup.render.graphic.Constants.Regexes.fileNameRegex
import com.github.zomb_676.fantasySoup.render.graphic.Constants.ShaderType
import com.github.zomb_676.fantasySoup.utils.getOrThrow
import com.github.zomb_676.fantasySoup.utils.math
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import org.lwjgl.opengl.GL43
import java.io.File
import kotlin.system.measureNanoTime


class  Shader(
    var shaderType: ShaderType,
    private val shaderString: String,
    val shaderName: String
) {
    companion object {
        private val allShaders: MutableList<Shader> = mutableListOf()
    }

    init {
        @Suppress("LeakingThis")
        allShaders.add(this)
    }

    private var shaderId: Int = -1
    private var attachedNum = 0

    constructor(shaderType: ShaderType, shaderPath: File) : this(shaderType, shaderPath.readText(), shaderPath.name)

    constructor(shaderType: ShaderType,shaderPath: ResourceLocation):this(shaderType,
        Minecraft.getInstance().resourceManager.getResource(shaderPath).inputStream.reader().readText()
            ,shaderPath.path.math(fileNameRegex)!!.value){
    }

    @Throws(RuntimeException::class)
    constructor(shaderPath: String) : this(
        ShaderType.getShaderTypeFromExtension(
            fileExtensionRegex.find(shaderPath, 0)
                .getOrThrow("can't resolve file extension string from $shaderPath").value
        ), File(shaderPath)
    )

    @Throws(RuntimeException::class)
    fun compileShader() {
        shaderId = GL43.glCreateShader(shaderType.type)
        GL43.glShaderSource(shaderId, shaderString)
        measureNanoTime { GL43.glCompileShader(shaderId) }
            .let { time->
                FantasySoup.logger.info(
                    Canvas.graphicMarker,
                    "compile shader: $shaderName , cost time : $time nanoseconds"
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