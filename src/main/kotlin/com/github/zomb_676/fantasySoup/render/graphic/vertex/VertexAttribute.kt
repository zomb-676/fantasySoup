package com.github.zomb_676.fantasySoup.render.graphic.vertex

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.Constants.VertexDataType

open class VertexAttribute(val vertexDataType: VertexDataType , val name:String = "not specific") {
    companion object{
        var index = 0
    }

    val attributeIndex = index++

}