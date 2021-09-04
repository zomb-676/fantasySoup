package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.render.graphic.texture.InnerTexture
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL43

class FrameBuffer(
    private val enableDepth: Boolean, private val enableStencil: Boolean,
    private var width: Int = -1, private var height: Int = -1
) {

    constructor(enableDepth: Boolean,enableStencil: Boolean):this(enableDepth, enableStencil,
        Minecraft.getInstance().window.width, Minecraft.getInstance().window.height)

    private var frameBufferId = -1
    lateinit var colorTexture: Texture//TODO support multi color attachment
    lateinit var depthTexture: Texture
    lateinit var stencilTexture: Texture

    @Throws(IllegalStateException::class)
    fun genFrameBuffer(): FrameBuffer {
        if (frameBufferId != -1) {
            throw IllegalStateException("can't generate a already generated texture")
        }

        frameBufferId = GL43.glGenFramebuffers()
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBufferId)
        val (width, height) = getSize()

        attachColor()
        if (enableDepth) attachDepth()
        if (enableStencil) attachStencil()

        if (GL43.glCheckFramebufferStatus(GL43.GL_FRAMEBUFFER) != GL43.GL_FRAMEBUFFER_COMPLETE) {
            throw IllegalStateException("failed to init frameBuffer")
        }
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, 0)
        return this
    }

    @Throws(IllegalStateException::class)
    fun deleteFrameBuffer() {
        if (frameBufferId == -1) {
            throw IllegalStateException("can't delete a already deleted texture")
        }
        GL43.glDeleteFramebuffers(frameBufferId)
        val (width, height) = getSize()
        frameBufferId = -1
        colorTexture.resize(width, height)
        if (enableDepth) depthTexture.resize(width, height)
        if (enableStencil) stencilTexture.resize(width, height)

        genFrameBuffer()

    }

    fun needResize(): Boolean =
        Minecraft.getInstance().window.let { this.width != it.width || this.height != it.height }

    fun getSize(): Pair<Int, Int> =
        Minecraft.getInstance().window.run { width to height }

    @Throws(IllegalStateException::class)
    fun bindFrameBuffer(): FrameBuffer {
        if (frameBufferId != -1) {
            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBufferId)
            return this
        } else throw IllegalStateException("trying to bind an unavailable frameBuffer")
    }

    fun unbindFrameBuffer(): FrameBuffer {
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, 0)
        return this
    }

    private fun attachColor(attachNum: Int = 0) {
        bindFrameBuffer()
        colorTexture = InnerTexture(width, height)
            .genTexture()
            .bindTexture()
        GL43.glFramebufferTexture2D(
            GL43.GL_FRAMEBUFFER, GL43.GL_COLOR_ATTACHMENT0 + attachNum,
            GL43.GL_TEXTURE_2D, colorTexture.textureID, 0
        )
    }

    private fun attachDepth() {
        bindFrameBuffer()
        depthTexture = InnerTexture(width, height)
            .genTexture()
            .bindTexture()
        GL43.glFramebufferTexture2D(
            GL43.GL_TEXTURE_2D, GL43.GL_DEPTH_ATTACHMENT,
            GL43.GL_TEXTURE_2D, depthTexture.textureID, 0
        )
    }

    private fun attachStencil() {
        stencilTexture = InnerTexture(width, height)
            .genTexture()
            .bindTexture()
        GL43.glFramebufferTexture2D(
            GL43.GL_TEXTURE_2D, GL43.GL_STENCIL_ATTACHMENT,
            GL43.GL_TEXTURE_2D, stencilTexture.textureID, 0
        )
    }


}