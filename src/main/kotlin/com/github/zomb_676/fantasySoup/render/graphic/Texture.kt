package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureFilterParameter.TextureFilterType.LINER
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType
import com.github.zomb_676.fantasySoup.render.graphic.Constants.TextureWrappingType.REPEAT
import org.lwjgl.opengl.GL43

abstract class Texture {
    companion object {
        val allTextures: MutableList<Texture> = mutableListOf()

        fun loadImage(){

        }
    }

    private var textureID = -1

    fun genTexture() {
        textureID = GL43.glGenTextures()
        GL43.glBindTexture(GL43.GL_TEXTURE_2D, textureID)
        setTexWrappingType()
        setTexParameter()

    }

    protected open fun setTexWrappingType(
        xWrappingType: TextureWrappingType = REPEAT,
        yWrappingType: TextureWrappingType = REPEAT
    ) {
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_S, xWrappingType.type)
        GL43.glTexParameteri(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_WRAP_T, yWrappingType.type)
    }

    @Throws(IllegalArgumentException::class)
    protected open fun setTexParameter(
        narrowFilterType: TextureFilterParameter =LINER,
        zoomFilterType: TextureFilterParameter = LINER
    ) {
        GL43.glTexParameterIi(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MIN_FILTER, narrowFilterType.type)

        when(zoomFilterType){
            is TextureFilterParameter.TextureFilterType->
                GL43.glTexParameterIi(GL43.GL_TEXTURE_2D, GL43.GL_TEXTURE_MAG_FILTER, zoomFilterType.type)
            is TextureFilterParameter.TextureMipmapFilterType->
                throw IllegalArgumentException("can't apply mipmap filter type ${zoomFilterType.name} " +
                        "to GL_TEXTURE_MAG_FILTER , because mipmap only apply to narrow , not zoom")
        }
    }

}