package com.github.zomb_676.fantasySoup.render.graphic.texture

import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.use
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack

class FileTexture(private val path: String, private val channels: Int = 0) : Texture() {
    override fun getImageData(): ImageData =
        MemoryStack.stackPush().use {
            STBImage.stbi_set_flip_vertically_on_load(true)
            val w = this.mallocInt(1)
            val h = this.mallocInt(1)
            val channel = this.mallocInt(1)
            val buffer = STBImage.stbi_load(path, w, h, channel, channels)
            check(buffer != null) { "failed to read image from path:$path , reason:${STBImage.stbi_failure_reason()}" }
            ImageData(w.get(), h.get(), channel.get(), buffer)
        }
}