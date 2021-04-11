package com.github.zomb_676.fantasySoup.shader

import org.lwjgl.opengl.GL43

open class ShaderProgram(val vertexShader: VertexShader, val fragShader: FragShader) {
    companion object{
        val programs = arrayListOf<ShaderProgram>()
    }
    val id : Int= GL43.glCreateProgram()
    init {
        programs.add(this)
        vertexShader.attach(this)
        fragShader.attach(this)
        GL43.glLinkProgram(id)
    }

    fun use() = GL43.glUseProgram(id)

    fun stop()=GL43.glUseProgram(0)

}