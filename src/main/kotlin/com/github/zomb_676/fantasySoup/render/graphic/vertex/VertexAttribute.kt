package com.github.zomb_676.fantasySoup.render.graphic.vertex

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.Constants.VertexDataType

class VertexAttribute(val vertexDataType: VertexDataType) {
    companion object{
        var index = 0
    }

    val attributeIndex = index++

}