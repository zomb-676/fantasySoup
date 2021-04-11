package com.github.zomb_676.fantasySoup.shader.program

import com.github.zomb_676.fantasySoup.shader.ShaderProgram
import com.github.zomb_676.fantasySoup.shader.frag.FullCircle
import com.github.zomb_676.fantasySoup.shader.vertex.Copy
import org.lwjgl.opengl.GL43

object FullCircleProgram : ShaderProgram(Copy, FullCircle) {
    fun setRadius(radius: Float) = GL43.glUniform1f(0, radius)
    fun setCenter(posX: Float, posY: Float) = GL43.glUniform2f(1, posX, posY)
    fun sendSampler() = GL43.glProgramUniform1i(id, 2, GL43.GL_TEXTURE0)
}

