package com.github.zomb_676.fantasySoup.gui

import com.github.zomb_676.fantasySoup.shader.program.FullCircleProgram
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.shader.Framebuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.opengl.GL43

/**
 * In charge of all draw work in gui
 */
//TODO highest priority
@OnlyIn(Dist.CLIENT)
@Suppress("unused","MemberVisibilityCanBePrivate")
object Canvas {
    private var r: Int = 1
    private var g: Int = 1
    private var b: Int = 1
    private var a: Int = 1
    /**
     * Depth
     */
    private var z : Double = 0.0
    private var minecraft = Minecraft.getInstance()
    private var window = minecraft.mainWindow
    private var itemRender = minecraft.itemRenderer

    /**
     * require this if src == dest
     */
//    val blitFrameBuffer = Framebuffer(0,0,true,Minecraft.IS_RUNNING_ON_MAC)

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
//        val frameBufferID = GL43.glGenFramebuffers()
//        val width = minecraft.mainWindow.width
//        val height = minecraft.mainWindow.height
//        GL43.glBindFramebuffer(GL43.GL_RENDERBUFFER,frameBufferID)
//        GL43.glRenderbufferStorage(
//            GL43.GL_RENDERBUFFER,GL43.GL_DEPTH24_STENCIL8,width,height,)
//
//    }

    fun drawFullCircle(posX:Float,posY:Float,radius:Float) {
        val mainFramebuffer = Minecraft.getInstance().framebuffer
//        runBefore(mainFramebuffer.frameBufferTexture,mainFramebuffer, blitFrameBuffer)


        GlStateManager.blendColor(1.0f,1.0f,1.0f,1.0f)
        FullCircleProgram.use()
        FullCircleProgram.setRadius(radius)
        FullCircleProgram.setCenter(posX, posY)
        uploadMainSampler()
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

    fun sampler(uniform: Int,texture : ResourceLocation,textureUnit: Int){
        GlStateManager.activeTexture(GL43.GL_TEXTURE0 + textureUnit)
        GlStateManager.enableTexture()
        val manger = Minecraft.getInstance().textureManager
        manger.bindTexture(texture)
        GL43.glUniform1i(uniform,textureUnit)
        GlStateManager.disableTexture()
        GlStateManager.activeTexture(GL43.GL_TEXTURE0)
    }

//    fun uploadMainSampler() = sampler(2,Minecraft.getInstance().framebuffer.frameBufferTexture,0)
    fun uploadMainSampler() = sampler(2,Minecraft.getInstance().framebuffer.frameBufferTexture,0)

//    private fun resizeBlitFrameBuffer(){
//        blitFrameBuffer.framebufferWidth = minecraft.mainWindow.width
//        blitFrameBuffer.framebufferHeight= minecraft.mainWindow.height
//    }

    private fun runBefore(src:Int,dst:Framebuffer,blit:Framebuffer){
        var intermediateDst : Framebuffer = dst
        if (src==dst.frameBufferTexture){
            intermediateDst=blit
        }
        intermediateDst.bindFramebuffer(true)
    }
    private fun updateWholeWindow()= upload(0f, window.height.toFloat(), window.width.toFloat(),0f)

    /**
     * @param location : layout(location) written in frag shader
     * @param texture : [Framebuffer.framebufferTexture]
     * @param textureUnit : index of texture to use
     */
    fun sampler(location:Int, texture:Int, textureUnit:Int){
        RenderSystem.activeTexture(GL43.GL_TEXTURE0 + textureUnit)
        RenderSystem.enableTexture()
        RenderSystem.bindTexture(texture)
        RenderSystem.glUniform1i(location,textureUnit)
    }
}












