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


class Shader(
    var shaderType: ShaderType,
    private var shaderString: String,
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
    private var getShaderSource: (() -> String)? = null

    constructor(shaderType: ShaderType, shaderPath: File) : this(shaderType, shaderPath.readText(), shaderPath.name) {
        getShaderSource = { shaderPath.readText() }
    }

    constructor(shaderType: ShaderType, shaderPath: ResourceLocation) : this(
        shaderType,
        Minecraft.getInstance().resourceManager.getResource(shaderPath).inputStream.reader().readText(),
        shaderPath.path.math(fileNameRegex)!!.value
    ) {
        getShaderSource =
            { Minecraft.getInstance().resourceManager.getResource(shaderPath).inputStream.reader().readText() }
    }

    @Throws(RuntimeException::class)
    constructor(shaderPath: String) : this(
        ShaderType.getShaderTypeFromExtension(
            fileExtensionRegex.find(shaderPath, 0)
                .getOrThrow("can't resolve file extension string from $shaderPath").value
        ), File(shaderPath)
    ) {
        getShaderSource = { File(shaderPath).readText() }
    }

    @Throws(RuntimeException::class)
    fun compileShader(isFromReload: Boolean = false) {
        shaderId = GL43.glCreateShader(shaderType.type)
        GL43.glShaderSource(shaderId, shaderString)
        measureNanoTime { GL43.glCompileShader(shaderId) }
            .let { time ->
                FantasySoup.logger.info(
                    Canvas.graphicMarker,
                    "${if (isFromReload) "re-" else ""}compile shader: $shaderName , cost time : $time nanoseconds"
                )
            }
        GL43.glGetShaderInfoLog(shaderId).takeIf { it.isNotEmpty() }?.let {
            println("shader status:$it")
            throw RuntimeException("failed to compile shader , reason $it")
        }
    }

    fun deleteShader() {
        FantasySoup.logger.info(Canvas.graphicMarker, "delete shader $this")
        GL43.glDeleteShader(shaderId)
        allShaders.remove(this)
        shaderId = -1
        attachedNum--
    }

    fun attachToProgram(programID: Int) {
        if (!isCompiled())
            compileShader()
        GL43.glAttachShader(programID, shaderId)
        attachedNum++
    }

    fun isCompiled() = shaderId != -1

    fun isReloadable() = getShaderSource != null

    override fun toString(): String = "{type:${shaderType.typeName},name:$shaderName}"

    @Throws(IllegalArgumentException::class)
    fun reloadShader(): Boolean {
        val newSource = getShaderSource?.invoke()
        if (newSource == shaderString) {
            FantasySoup.logger.info(Canvas.graphicMarker, "trying to reload a same reloadable shader : $this")
            FantasySoup.logger.debug(Canvas.graphicMarker, "shader string:$shaderString")
            return false
        }
        if (newSource == null) {
            FantasySoup.logger.info(Canvas.graphicMarker, "trying to reload a not reloadable shader : $this")
            throw IllegalArgumentException("trying to reload a not reloadable shader : $this")
        }
        this.deleteShader()
        shaderString = newSource
        this.compileShader(true)
        return true
    }
}