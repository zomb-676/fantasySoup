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

    /**
     * if use [TextureWrappingType.CLAMP_TO_BORDER]
     *
     * you need to specific border color manually
     *
     * like : GL43.glTexParameterfv(GL43.GL_TEXTURE_2D,GL43.GL_TEXTURE_BORDER_COLOR,clolor data)
     */
    enum class TextureWrappingType(val type:Int,val typeName:String){
        REPEAT(GL43.GL_REPEAT,"repeat"),
        MIRRORED_REPEAT(GL43.GL_MIRRORED_REPEAT,"mirrored repeat"),
        CLAM_TO_EDGE(GL43.GL_CLAMP_TO_EDGE,"clamp to edge"),
        CLAMP_TO_BORDER(GL43.GL_CLAMP_TO_BORDER,"clamp to border")
    }

    sealed interface TextureFilterParameter{

        val type : Int
        val typeName : String

        enum class TextureFilterType(override val type:Int, override val typeName: String) : TextureFilterParameter {
            NEAREST(GL43.GL_NEAREST, "Nearest Neighbor Filtering"),
            LINER(GL43.GL_LINEAR, "(Bi)linear Filtering");
        }

        /**
         * opengl naming convention
         *
         * first determines how to find the texture on the mipmap ,
         * similar to [TextureFilterType]
         *
         * second determines how to find the mipmap
         *
         * not apply this to GL_TEXTURE_MAG_FILTER
         */
        enum class TextureMipmapFilterType(override val type:Int, override val typeName: String) : TextureFilterParameter{
            MIPMAP_NEAREST_SAMPLER_NEAREST(GL43.GL_NEAREST_MIPMAP_NEAREST,"mipmap nearest sampler nearest"),
            MIPMAP_NEAREST_SAMPLER_LINEAR(GL43.GL_LINEAR_MIPMAP_NEAREST,"mipmap nearest sampler linear"),
            MIPMAP_LINEAR_SAMPLER_NEAREST(GL43.GL_NEAREST_MIPMAP_LINEAR,"mipmap linear sampler nearest"),
            MIPMAP_LINEAR_SAMPLER_LINEAR(GL43.GL_LINEAR_MIPMAP_LINEAR,"mipmap linear sampler linear");
        }
    }


}