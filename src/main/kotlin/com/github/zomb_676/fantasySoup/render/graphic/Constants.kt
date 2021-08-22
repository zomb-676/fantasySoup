package com.github.zomb_676.fantasySoup.render.graphic

import org.lwjgl.opengl.GL43

object Constants {

    object Regexes{
        /**
         * regex:(?!\.)[a-z]+$
         * math file extension exclude .
         */
        val fileExtensionRegex = Regex("(?!\\.)[a-z]+\$")

        /**
         * regex:(?<=[/\\])[^/\\]+\..+ , math file name
         */
        val fileNameRegex = Regex("(?<=[/\\\\])[^/\\\\]+\\..+")
    }

    enum class ShaderType(val type: Int, val typeName: String) {
        VERTEX(GL43.GL_VERTEX_SHADER, "vertex shader"),
        FRAGMENT(GL43.GL_FRAGMENT_SHADER, "fragment shader");

        companion object {
            @Throws(RuntimeException::class)
            fun getShaderTypeFromExtension(extension: String) =
                when (extension) {
                    "vsh" -> VERTEX
                    "fsh" -> FRAGMENT
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
    enum class TextureWrappingType(val type: Int, val typeName: String) {
        REPEAT(GL43.GL_REPEAT, "repeat"),
        MIRRORED_REPEAT(GL43.GL_MIRRORED_REPEAT, "mirrored repeat"),
        CLAM_TO_EDGE(GL43.GL_CLAMP_TO_EDGE, "clamp to edge"),
        CLAMP_TO_BORDER(GL43.GL_CLAMP_TO_BORDER, "clamp to border")
    }

    sealed interface TextureFilterParameter {

        val type: Int
        val typeName: String

        enum class TextureFilterType(override val type: Int, override val typeName: String) : TextureFilterParameter {
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
        enum class TextureMipmapFilterType(override val type: Int, override val typeName: String) :
            TextureFilterParameter {
            MIPMAP_NEAREST_SAMPLER_NEAREST(GL43.GL_NEAREST_MIPMAP_NEAREST, "mipmap nearest sampler nearest"),
            MIPMAP_NEAREST_SAMPLER_LINEAR(GL43.GL_LINEAR_MIPMAP_NEAREST, "mipmap nearest sampler linear"),
            MIPMAP_LINEAR_SAMPLER_NEAREST(GL43.GL_NEAREST_MIPMAP_LINEAR, "mipmap linear sampler nearest"),
            MIPMAP_LINEAR_SAMPLER_LINEAR(GL43.GL_LINEAR_MIPMAP_LINEAR, "mipmap linear sampler linear");
        }
    }

    /**
     * indic the opengl type
     */
    enum class InternalType(
        val size: Int, val glType: Int
    ) {
        FLOAT(Float.SIZE_BYTES, GL43.GL_FLOAT),
        DOUBLE(Double.SIZE_BYTES,GL43.GL_DOUBLE),
        BYTE(Byte.SIZE_BYTES, GL43.GL_BYTE),
        UNSIGNED_BYTE(Byte.SIZE_BYTES, GL43.GL_UNSIGNED_BYTE),
        SHORT(Short.SIZE_BYTES, GL43.GL_SHORT),
        UNSIGNED_SHORT(Short.SIZE_BYTES, GL43.GL_UNSIGNED_SHORT),
        INT(Int.SIZE_BYTES, GL43.GL_INT),
        UINT(Int.SIZE_BYTES, GL43.GL_UNSIGNED_INT),
        HALF_FLOAT(Float.SIZE_BYTES, GL43.GL_HALF_FLOAT);
    }

    /**
     * indic the type in Vertex Shader
     *
     * @param count how many element this type has
     * @param internalType the internal type
     * @property size how many memory it uses , measured in byte
     */
    enum class VertexDataType(val count: Int, val internalType: InternalType) {

        FLOAT(1, InternalType.FLOAT),
        VEC2(2, InternalType.FLOAT),
        VEC3(3, InternalType.FLOAT),
        VEC4(4, InternalType.FLOAT),
        MAT3(9, InternalType.FLOAT),
        MAT4(1, InternalType.FLOAT),

        INT(1,InternalType.INT),
        DOUBLE(1,InternalType.DOUBLE);

        val size: Int = count * internalType.size

    }

    /**
     * tell the gpu whether to put data in high speed cache or not
     */
    enum class VertexStorageType(val type:Int, val typeName:String){
        /**
         * not change or nearly not change
         */
        STATIC_DRAW(GL43.GL_STATIC_DRAW,"static draw"),

        /**
         * change a lot
         */
        DYNAMIC_DRAW(GL43.GL_DYNAMIC_DRAW,"dynamic draw"),

        /**
         * change every time when drawing
         */
        STREAM_DRAW(GL43.GL_STREAM_DRAW,"stream draw");
    }

}