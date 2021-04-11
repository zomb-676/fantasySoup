package com.github.zomb_676.fantasySoup.gui

import com.github.zomb_676.fantasySoup.shader.frag.FullCircle
import com.github.zomb_676.fantasySoup.shader.program.FullCircleProgram
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.opengl.GL43
import kotlin.math.min

/**
 * In charge of all draw work in gui
 */
//TODO highest priority
@OnlyIn(Dist.CLIENT)
@Suppress("unused")
object Canvas {
    private var r: Int = 255
    private var g: Int = 255
    private var b: Int = 255
    private var a: Int = 255
    /**
     * Depth
     */
    private var z : Double = 0.0
    private var minecraft = Minecraft.getInstance()
    private var window = minecraft.mainWindow
    private var itemRender = minecraft.itemRenderer

    fun setRGBA(r: Int, g: Int, b: Int, a: Int) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun setColor(color: Int) {
        a = color shr 24 and 0xff
        r = color shr 16 and 0xff
        g = color shr 8 and 0xff
        b = color and 0xff
    }

    fun setAlpha(a: Int) {
        this.a = a
    }

    fun resetColor() {
        r = 255
        g = 255
        b = 255
        a = 255
    }

    fun setZ(z: Double) {
        this.z = z
    }
//    init {
//        val frameBuffer = GL43.glGenFramebuffers()
//        GL43.glBindTexture(GL43.GL_TEXTURE_2D,frameBuffer)
//        GlStateManager.framebufferTexture2D()
//    }

    fun drawFullCircle(posX:Float,posY:Float,radius:Float) {
        FullCircleProgram.use()
        FullCircleProgram.setRadius(radius)
        FullCircleProgram.setCenter(posX, posY)
//        RenderSystem.activeTexture(GL43.GL_TEXTURE0)
//        FullCircleProgram.sendSampler()
        val frameBuffer = minecraft.framebuffer
//        RenderSystem.bindTexture(frameBuffer.frameBufferTexture)
//        GL43.glUniform1i(2,)

        upload(posX-radius,posY-radius,posX+radius,posY+radius)
        FullCircleProgram.stop()
    }

     private fun upload(left:Float,top:Float,right:Float,bottom:Float) {
         val builder = Tessellator.getInstance().buffer
        builder.begin(GL43.GL_QUADS,DefaultVertexFormats.POSITION_COLOR)
        builder.pos(left.toDouble(), bottom.toDouble(),0.0).color(r,g,b,a).endVertex()
        builder.pos(right.toDouble(), bottom.toDouble(), 0.0).color(r, g, b, a).endVertex()
        builder.pos(right.toDouble(), top.toDouble(), 0.0).color(r, g, b, a).endVertex()
        builder.pos(left.toDouble(), top.toDouble(), 0.0).color(r, g, b, a).endVertex()
        builder.finishDrawing()
        WorldVertexBufferUploader.draw(builder)
    }

}