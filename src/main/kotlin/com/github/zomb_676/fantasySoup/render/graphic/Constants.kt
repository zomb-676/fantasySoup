package com.github.zomb_676.fantasySoup.render.graphic

import org.lwjgl.opengl.GL43

object Constants {
    enum class ShaderType(val type:Int,val typeName:String){
        VERTEX(GL43.GL_VERTEX_SHADER,"vertex shader"),
        FRAGMENT_SHADER(GL43.GL_FRAGMENT_SHADER,"fragment shader");

        companion object{
            @Throws(RuntimeException::class)
            fun getShaderTypeFromExtension(extension:String)=
                when(extension){
                    "vsh" -> VERTEX
                    "fsh" -> FRAGMENT_SHADER
                    else -> throw RuntimeException("failed to get shaderType from extension name $extension")
                }
        }

    }
}