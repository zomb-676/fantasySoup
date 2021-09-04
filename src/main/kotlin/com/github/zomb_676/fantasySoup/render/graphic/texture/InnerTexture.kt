package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Canvas
import com.github.zomb_676.fantasySoup.utils.rough
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL43
import org.lwjgl.stb.STBImageWrite
import org.lwjgl.system.MemoryUtil

class InnerTexture(width: Int, height: Int) : Texture(width, height) {

    constructor() : this(Minecraft.getInstance().window.width, Minecraft.getInstance().window.height)

    @Throws(RuntimeException::class)
    override fun getImageData(): ImageData {
        val pixels = MemoryUtil.memAlloc(channels * width * height)
        when (channels) {
            3 -> GL43.glReadPixels(0, 0, width, height, GL43.GL_RGB, GL43.GL_UNSIGNED_BYTE, pixels)
            4 -> GL43.glReadPixels(0, 0, width, height, GL43.GL_RGBA, GL43.GL_UNSIGNED_BYTE, pixels)
            else -> throw RuntimeException("nonsupport to get image data with channel $channels")
        }
        return ImageData(width, height, channels, pixels)
    }

    @Throws(IllegalArgumentException::class)
    fun saveImage(format: String, path: String) {
        getImageData().use {
            when (format.rough()) {
                "bmp" -> STBImageWrite.stbi_write_bmp(path, width, height, channels, it.data)
                "png" -> STBImageWrite.stbi_write_png(path, width, height, channels, it.data, width * channels)
                "jpg" -> STBImageWrite.stbi_write_png(path, width, height, channels, it.data, width * channels)
                else -> throw IllegalArgumentException("nonsupport image format : $format")
            }
            Unit
        }
    }

    override fun getTextureName(): String = "InnerTexture ${hashCode()}"

    override fun genTexture(): Texture {
        if (textureID != -1) {
            FantasySoup.logger.info(Canvas.graphicMarker, "trying to load a loaded texture called ${getTextureName()}")
            return this
        }
        textureID = GL43.glGenTextures()
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
        setTexWrappingType()
        setTexParameter()//don't use mipmap
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, 0)
        return this
    }

    override fun resize(width: Int, height: Int) {
        deleteTexture()
        genTexture()
    }
}