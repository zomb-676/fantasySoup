package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter.TextureFilterType.LINER
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType.REPEAT
import org.lwjgl.opengl.GL43
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

abstract class Texture(var width: Int = -1, var height: Int = -1) {
    companion object {
        private val allTextures: MutableList<Texture> = mutableListOf()
    }

    var textureID = -1
    var channels: Int = -1

    abstract fun getImageData(): ImageData
    protected abstract fun getTextureName(): String

    /**
     * unbind self after gen
     */
    abstract fun genTexture(): Texture

    abstract fun resize(width: Int, height: Int)

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
        return false
    }

    @Throws(RuntimeException::class)
    fun bindTexture(slotIndex: Int = 0): Texture {
        if (textureID != -1) {
            GL43.glActiveTexture(GL43.GL_TEXTURE0 + slotIndex)
            GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
        } else
            throw RuntimeException("try to bind an invalid texture")
        return this
    }

    fun deleteTexture() {
        GL43.glDeleteTextures(textureID)
        textureID = -1
    }

    data class ImageData(val width: Int, val height: Int, val channels: Int, val data: ByteBuffer) : AutoCloseable {
        private fun release() = STBImage.stbi_image_free(data)
        override fun close() = release()
    }

}