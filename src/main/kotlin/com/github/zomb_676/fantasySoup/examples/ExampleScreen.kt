package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.gui.IScreen
import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.MemoryFunctions.calculateFloatSize
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions.assertNoError
import com.github.zomb_676.fantasySoup.render.graphic.Program
import com.github.zomb_676.fantasySoup.render.graphic.Shader
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexArrayObject
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexAttribute
import com.github.zomb_676.fantasySoup.render.graphic.vertex.VertexBufferObject
import com.github.zomb_676.fantasySoup.utils.modResourcesLocation
import com.github.zomb_676.fantasySoup.utils.runTry
import com.github.zomb_676.fantasySoup.utils.wrapTry
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL43
import org.lwjgl.system.MemoryUtil
import java.io.File

class ExampleScreen(container: ExampleContainer, inventory: Inventory, pTitle: Component) :
    IScreen<ExampleContainer>(container, inventory, pTitle) {

    companion object {
        val frameBuffer: Int = runTry {
            val frameBuffer = GL43.glGenFramebuffers()
            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBuffer)
            val frameBufferTex = GL43.glGenTextures()
            GL43.glActiveTexture(GL43.GL_TEXTURE3)
            GL43.glBindTexture(GL43.GL_TEXTURE_2D, frameBufferTex)
            val window = Minecraft.getInstance().window
            GL43.glTexImage2D(
                GL43.GL_TEXTURE_2D, 0, GL43.GL_RGBA8, window.width, window.height,
                0, GL43.GL_RGBA, GL43.GL_UNSIGNED_BYTE, MemoryUtil.NULL
            )
            GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, GL43.GL_LINEAR)
            GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, GL43.GL_LINEAR)
            GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_S, GL43.GL_CLAMP_TO_EDGE)
            GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_T, GL43.GL_CLAMP_TO_EDGE)
            GL43.glFramebufferTexture2D(
                GL43.GL_FRAMEBUFFER, GL43.GL_COLOR_ATTACHMENT0,
                GL43.GL_TEXTURE_2D, frameBufferTex, 0
            )
            takeIf { GL43.glCheckFramebufferStatus(GL43.GL_FRAMEBUFFER) == GL43.GL_FALSE }?.run {
                println("failed to create frame buffer")
            }

            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, 0)
            GL43.glGenTextures().apply {
                GL43.glActiveTexture(GL43.GL_TEXTURE2)
                GL43.glBindTexture(GL43.GL_TEXTURE_2D, this)
                GL43.glActiveTexture(GL43.GL_TEXTURE0)
            }
            assertNoError()
//            Minecraft.getInstance().mainRenderTarget.unbindWrite()
            frameBuffer
        }
        private val programBlur = runTry { Program(
            Shader(Constants.ShaderType.VERTEX, modResourcesLocation("shader/vertex/tex_pos.vsh")),
            Shader(Constants.ShaderType.FRAGMENT, modResourcesLocation("shader/fragment/blur.fsh"))
        ).linkProgram() }
        private val programDrawFull = runTry { Program(
            Shader(Constants.ShaderType.VERTEX, modResourcesLocation("shader/vertex/basic.vsh")),
            Shader(Constants.ShaderType.FRAGMENT, modResourcesLocation("shader/fragment/basic.fsh"))
        ).linkProgram() }
        private val programTest = runTry { Program(
            Shader(Constants.ShaderType.VERTEX, modResourcesLocation("shader/vertex/rectangle.vsh")),
            Shader(Constants.ShaderType.FRAGMENT, modResourcesLocation("shader/fragment/rectangle.fsh"))
        ).linkProgram() }

        private val pos = floatArrayOf(
            -1.0f, -1.0f, 0.0f,     0f, 0f,
            1.0f, -1.0f, 0.0f,      1.0f, 0f,
            1.0f, 1.0f, 0.0f,       1.0f, 1.0f,

            -1.0f, -1.0f, 0.0f,     0f, 0f,
            1.0f, 1.0f, 0.0f,       1.0f, 1.0f,
            -1.0f, 1.0f, 0.0f,      0f, 1.0f,
        )
        private val posTest = floatArrayOf(
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,

            -1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f
        )

        private val vaoTest: VertexArrayObject = runTry {
            val vao = VertexArrayObject()
                .genVertexArrayObject()
                .bindVertexArrayObject()
            val vboTest: VertexBufferObject = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
                .genVertexBufferObject()
                .bindVertexBufferObject()
                .bindDate(posTest)

            vao.pushVertexType(VertexAttribute(Constants.VertexDataType.VEC3, "pos"))
                .setupByAttributePointer()
            vao
        }
        private val vaoId = runTry {
            val vaoId = GL43.glGenVertexArrays()
            GL43.glBindVertexArray(vaoId)

            val bufferId =GL43.glGenBuffers()
            GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER,bufferId)
            GL43.glBufferData(GL43.GL_ARRAY_BUFFER, posTest,GL43.GL_STATIC_DRAW)

            val bindingIndex = 2
            GL43.glEnableVertexAttribArray(0)
//            GL43.glVertexAttribPointer(0,3,GL43.GL_FLOAT,false,3.calculateFloatSize
//                ,0)
            GL43.glVertexAttribFormat(0,3,GL43.GL_FLOAT,false,0)
            GL43.glVertexAttribBinding(0,bindingIndex)
            GL43.glBindVertexBuffer(bindingIndex,vaoId,0,3.calculateFloatSize)

            GL43.glDisableVertexAttribArray(0)
            GL43.glBindVertexArray(0)
            vaoId
        }

        private val vaoFull: VertexArrayObject = runTry {
            val vaoFull = VertexArrayObject()
                .genVertexArrayObject()
                .bindVertexArrayObject()

            val vboFull: VertexBufferObject = VertexBufferObject(Constants.VertexStorageType.STATIC_DRAW)
                .genVertexBufferObject()
                .bindVertexBufferObject()
                .bindDate(pos)

            vaoFull.pushVertexType(VertexAttribute(Constants.VertexDataType.VEC3, "pos"))
                .pushVertexType(VertexAttribute(Constants.VertexDataType.VEC2, "tex"))
                .setupByAttributePointer()

            vaoFull
        }

        val c = runTry {
            GL43.glBindVertexArray(0)

//            OpenglFunctions.addGlKeyCallback(Minecraft.getInstance().window.window) { key: Int, scancode: Int, action: Int, mods: Int ->
//                if (key == GLFW.GLFW_KEY_R) {
//                    programBlur.reloadProgram()
//                    programTest.reloadProgram()
//                    programDrawFull.reloadProgram()
//                    println("reload success")
//                }
//            }
        }
    }

    override fun renderBg(poseStack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        val textureID = minecraft!!.mainRenderTarget.colorTextureId
        wrapTry{
            GL43.glActiveTexture(GL43.GL_TEXTURE4)
            GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
            GL43.glActiveTexture(GL43.GL_TEXTURE0)
//        val shaderBackup = RenderSystem.getShader()

            vaoFull.bindVertexArrayObject()
            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, frameBuffer)
            GL43.glClear(GL43.GL_COLOR_BUFFER_BIT)
            programDrawFull.useProgram()
            GL43.glUniform1i(1, 4)//texture
            GL43.glDrawArrays(GL43.GL_TRIANGLES, 0, 6)
            GL43.glBindVertexArray(0)

            vaoFull.bindVertexArrayObject()
            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, minecraft!!.mainRenderTarget.frameBufferId)
//        GL43.glClear(GL43.GL_COLOR_BUFFER_BIT)
            programBlur.useProgram()
            GL43.glUniform2f(0, 5f, 5f)//BlurDir
            GL43.glUniform1f(1, 3f)//radius
            GL43.glUniform1i(2, 3)//texture
            GL43.glUniform2f(3, 1f / width, 1f / height)//oneTexel
            GL43.glDrawArrays(GL43.GL_TRIANGLES, 0, 6)

            GL43.glBindVertexArray(0)
        }.invoke()

       wrapTry {
            vaoFull.bindVertexArrayObject()
            GL43.glBindFramebuffer(GL43.GL_FRAMEBUFFER, minecraft!!.mainRenderTarget.frameBufferId)
            vaoTest.bindVertexArrayObject()
            vaoTest.bindVertexArrayObject()
            programTest.useProgram()
            GL43.glDrawArrays(GL43.GL_TRIANGLES, 0, 6)
            GL43.glBindVertexArray(0)
//            GL43.glUseProgram(shaderBackup!!.id)
//            RenderSystem.setShader { shaderBackup }
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBg(poseStack, partialTicks, mouseX, mouseY)
    }
}