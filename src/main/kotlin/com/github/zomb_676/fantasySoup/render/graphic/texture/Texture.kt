package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter.TextureFilterType.LINER
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType.REPEAT
import org.lwjgl.opengl.GL43
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

abstract class Texture {
    companion object {
        private val allTextures: MutableList<Texture> = mutableListOf()
    }

    private var textureID = -1

    protected abstract fun getImageData(): ImageData

    fun genTexture() {
        textureID = GL43.glGenTextures()
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
        setTexWrappingType()
        val needMipmap = setTexParameter()
        getImageData().use {
            val internalFormat = if (it.channels == 3) GL43.GL_RGB else GL43.GL_RGBA
            GL43.glTexImage2D(
                GL43.GL_TEXTURE_2D, 0, internalFormat,
                it.width, it.height, 0, internalFormat, GL43.GL_UNSIGNED_BYTE, it.data
            )
            if (needMipmap) GL43.glGenerateMipmap(GL43.GL_TEXTURE_2D)
        }
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, 0)
    }

    protected open fun setTexWrappingType(
        xWrappingType: TextureWrappingType = REPEAT,
        yWrappingType: TextureWrappingType = REPEAT
    ) {
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_S, xWrappingType.type)
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_T, yWrappingType.type)
    }

    /**
     * @return true if this need set mipmap
     */
    @Throws(IllegalArgumentException::class)
    protected open fun setTexParameter(
        narrowFilterType: TextureFilterParameter = LINER,
        zoomFilterType: TextureFilterParameter = LINER
    ): Boolean {
        GL43.glTexParameterIi(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, narrowFilterType.type)
        when (zoomFilterType) {
            is TextureFilterParameter.TextureFilterType ->
                GL43.glTexParameterIi(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, zoomFilterType.type)
            is TextureFilterParameter.TextureMipmapFilterType ->
                throw IllegalArgumentException(
                    "can't apply mipmap filter type ${zoomFilterType.name} " +
                            "to GL_TEXTURE_MAG_FILTER , because mipmap only apply to narrow , not zoom"
                )
        }
        if (narrowFilterType is TextureFilterParameter.TextureMipmapFilterType) return true
        if (narrowFilterType is TextureFilterParameter.TextureFilterType) return true
        return false
    }

    @Throws(RuntimeException::class)
    fun bindTexture() {
        if (textureID != -1)
            GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
        else
            throw RuntimeException("try to bind an invalid texture")
    }

    fun deleteTexture(){
        GL43.glDeleteTextures(textureID)
        textureID=-1
    }

    data class ImageData(val width: Int, val height: Int, val channels: Int, val data: ByteBuffer) : AutoCloseable {
        private fun release() = STBImage.stbi_image_free(data)
        override fun close() = release()
    }

}