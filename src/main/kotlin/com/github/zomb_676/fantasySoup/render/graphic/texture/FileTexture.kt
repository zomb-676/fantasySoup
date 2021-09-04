package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Canvas
import com.github.zomb_676.fantasySoup.render.graphic.Constants.Regexes.fileNameRegex
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.use
import org.lwjgl.opengl.GL43
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack

/**
 * @param desiredChannels 0 for auto
 */
open class FileTexture(private val path: String, private val desiredChannels: Int = 0) : Texture() {

    @Throws(UnsupportedOperationException::class)
    override fun resize(width:Int,height:Int) = throw UnsupportedOperationException("fileTexture doesn't this operation")

    /**
     * @return [Texture.ImageData] implement [AutoCloseable]
     *
     * , so use by [AutoCloseable.use] , and the data will be released automatically
     */
    override fun getImageData(): ImageData =
        MemoryStack.stackPush().use {
            STBImage.stbi_set_flip_vertically_on_load(true)
            val w = this.mallocInt(1)
            val h = this.mallocInt(1)
            val channel = this.mallocInt(1)
            val buffer = STBImage.stbi_load(path, w, h, channel, desiredChannels)
            check(buffer != null) { "failed to read image from path:$path , reason:${STBImage.stbi_failure_reason()}" }
            ImageData(w.get(), h.get(), channel.get(), buffer)
        }

    override fun genTexture(): Texture {
        if (textureID != -1) {
            FantasySoup.logger.info(Canvas.graphicMarker, "trying to load a loaded texture called ${getTextureName()}")
            return this
        }
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
            width = it.width
            height = it.height
            channels = it.channels
            if (needMipmap) GL43.glGenerateMipmap(GL43.GL_TEXTURE_2D)
        }
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, 0)
        return this
    }

    override fun getTextureName(): String = fileNameRegex.find(path)!!.value
}