package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.render.graphic.Constants.Regexes.fileNameRegex
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.use
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack

/**
 * @param desiredChannels 0 for auto
 */
open class FileTexture(private val path: String, private val desiredChannels: Int = 0) : Texture() {

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

    override fun getTextureName(): String = fileNameRegex.find(path)!!.value
}