package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.gui.IScreen
import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.Program
import com.github.zomb_676.fantasySoup.render.graphic.Shader
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexArrayObject
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexAttribute
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexBufferObject
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.lwjgl.opengl.GL43
import org.lwjgl.opengl.GL45
import org.lwjgl.system.MemoryUtil
import java.io.File

class ExampleScreen(container: ExampleContainer, inventory: Inventory, pTitle: Component) :
    IScreen<ExampleContainer>(container, inventory, pTitle) {
    init {
        println("initing !")
    }
    private val frameBuffer: Int
    private val programBlur = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/main/resources/assets/fantasy_soup/shader/vertex/TexPos.vsh")),
        Shader(Constants.ShaderType.FRAGMENT_SHADER, File("src/main/resources/assets/fantasy_soup/shader/fragment/blur.fsh"))
    ).linkProgram()
    private val programDrawFull = Program(
        Shader(Constants.ShaderType.VERTEX, File("src/main/resources/assets/fantasy_soup/shader/vertex/basic.vsh")),
        Shader(Constants.ShaderType.FRAGMENT_SHADER, File("src/main/resources/assets/fantasy_soup/shader/fragment/basic.fsh"))
    ).linkProgram()
    private val pos = floatArrayOf(
        -1.0f, -1.0f, 0.0f, -1.0f, -1.0f,
        1.0f, -1.0f, 0.0f, 1.0f, -1.0f,
        1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        -1.0f, 1.0f, 0.0f, -1.0f, 1.0f
    )
    private val vaoFull: VertexArrayObject
    private val vboFull: VertexBufferObject

    init {
        // framebuffer setup
        frameBuffer = GL43.glGenFramebuffers()
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBuffer)
        val frameBufferTex = GL43.glGenTextures()
        GL43.glActiveTexture(GL43.GL_TEXTURE1)
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, frameBufferTex)
        GL43.glTexImage2D(
            GL43.GL_TEXTURE_2D, 0, GL43.GL_RGBA8, width, height,
            0, GL43.GL_RGBA, GL43.GL_UNSIGNED_BYTE, MemoryUtil.NULL
        )
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, GL43.GL_LINEAR)
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, GL43.GL_LINEAR)
        GL43.glFramebufferTexture2D(
            GL43.GL_FRAMEBUFFER, GL43.GL_COLOR_ATTACHMENT0,
            GL43.GL_TEXTURE_2D, frameBufferTex, 0
        )

        vaoFull = VertexArrayObject()
            .genVertexArrayObject()
            .bindVertexArrayObject()

        vboFull = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
            .genVertexBufferObject()
            .bindVertexBufferObject()
            .bindDate(pos)

        vaoFull.pushVertexType(VertexAttribute(Constants.VertexDataType.VEC3, "pos"))
            .pushVertexType(VertexAttribute(Constants.VertexDataType.VEC2, "tex"))
            .setup()
    }

    override fun renderBg(poseStack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        val textureID = minecraft!!.mainRenderTarget.colorTextureId
        GL43.glActiveTexture(GL43.GL_TEXTURE2)
        GL43.glBindTexture(GL43.GL_TEXTURE_2D,textureID)
        val shaderBackup = RenderSystem.getShader()

        vaoFull.bindVertexArrayObject()
        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER,frameBuffer)
        programDrawFull.useProgram()
        GL43.glUniform1i(1,2)
        GL43.glDrawArrays(GL43.GL_QUADS,0,4)

        GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER,minecraft!!.mainRenderTarget.frameBufferId)
        programBlur.useProgram()
        GL43.glUniform2f(0, 5f, 5f)//BlurDir
        GL43.glUniform1f(1, 3f)//radius
        GL43.glUniform1i(2, 1)//texture
        GL43.glUniform2f(3, 1f / width, 1f / height)//oneTexel
        GL43.glDrawArrays(GL45.GL_TRIANGLES, 0, 4)

//        GL43.glBindVertexArray()
        GL43.glUseProgram(shaderBackup!!.id)
//        RenderSystem.setShader { shaderBackup }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBg(poseStack, partialTicks, mouseX, mouseY)
    }
}