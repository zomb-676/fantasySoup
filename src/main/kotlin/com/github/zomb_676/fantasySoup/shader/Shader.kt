package com.github.zomb_676.fantasySoup.shader

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.client.Minecraft
import net.minecraft.resources.IResourceManager
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL20.*
import java.io.IOException

/**
 * @param shaderType : [org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER]
 * and [org.lwjgl.opengl.GL20.GL_VERTEX_SHADER]
 */
open class Shader @Throws(IOException::class)
constructor(manger: IResourceManager,val location: ResourceLocation, shaderType: Int) {
    var isDeleted: Boolean = false
    var attachedCount: Int = 0
    val id: Int

    companion object {
        val shaders = arrayListOf<Shader>()
        fun deleteAll() = shaders.map { it.delete() }
    }

    init {
        manger.getResource(location).inputStream.buffered().use {
            val shaderContent = String(it.readBytes())
            if (shaderContent != "") {
                id = glCreateShader(shaderType)
                glShaderSource(id, shaderContent)
                glCompileShader(id)
                if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_TRUE) {
                    FantasySoup.logger.info("successfully load and compile shader ${location.path}")
                } else {
                    throw RuntimeException("failed to compile shader ${location.path}\nreason: ${glGetShaderInfoLog(id).trim()}")
                }
            } else {
                throw RuntimeException("trying to read an unavailable shader , path:${location.path}")
            }
        }
        shaders.add(this)
    }

    fun attach(shaderProgram: ShaderProgram) {
        if (isDeleted) {
            throw RuntimeException(
                "trying to attach a deleted shader  ${this::class.simpleName}" +
                        " to program ${shaderProgram::class.simpleName}"
            )
        }
        glAttachShader(shaderProgram.id, id)
    }

    fun detach(shaderProgram: ShaderProgram) {
        if (attachedCount==0) {
            glDetachShader(shaderProgram.id, id)
        }else{
            throw RuntimeException("trying to detach shader:${this::class.simpleName} " +
                    "from program ${shaderProgram::class.simpleName}"+
                    "which is still attached to other program")
        }
    }

    fun delete() {
        if (!isDeleted) {
            glDeleteShader(id)
            isDeleted = true
        } else {
            FantasySoup.logger.error("try to delete a deleted shader ${this::class.simpleName}")
        }
    }
}