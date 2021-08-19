package com.github.zomb_676.fantasySoup.render.graphic.vertex

import org.lwjgl.opengl.GL43

/**
 * @property bindingIndex
 * @property arrayID the vertex data ID
 * @property vertexIndexCount Self-increasing with the push of VertexAttribute
 */
class VertexArrayObject {
    private var vertexArrayObjectId = -1
    private val types = mutableListOf<AttributeIndexNormal>()
    private val bindingIndex = bindingIndexTotal++
    private val arrayID = 0
    private var vertexIndexCount = 0

    companion object{
        private var bindingIndexTotal = 0
    }

    fun genVertexArrayObject(): VertexArrayObject {
        vertexArrayObjectId = GL43.glGenVertexArrays()
        return this
    }

    fun pushVertexType(vertexAttribute: VertexAttribute,needNormalized: Boolean=false): VertexArrayObject {
        types.add(AttributeIndexNormal(vertexAttribute,vertexIndexCount++,needNormalized))
        return this
    }

    fun bindVertexArrayObject(): VertexArrayObject {
        GL43.glBindVertexArray(vertexArrayObjectId)
        return this
    }

    fun setup(): VertexArrayObject {
        this.bindVertexArrayObject()
        var stride = 0
        for (attributeIndexNormal in types){
            val vertexIndex = attributeIndexNormal.vertexIndex
            val data = attributeIndexNormal.attributeData
            GL43.glEnableVertexAttribArray(vertexIndex)
            GL43.glVertexAttribFormat(data.attributeIndex,data.vertexDataType.count
                ,data.vertexDataType.internalType.glType,attributeIndexNormal.needNormalized,stride)
            GL43.glVertexAttribBinding(vertexIndex,bindingIndex)
            stride+=data.vertexDataType.size
        }
        GL43.glBindVertexBuffer(bindingIndex,vertexArrayObjectId,0,stride)
        endUse()
        return this
    }

    fun endUse(){
        GL43.glBindVertexArray(0)
        (0 until types.size).forEach { GL43.glDisableVertexAttribArray(it) }
    }

    /**
     * @param vertexIndex inconsistent with layout (location = num)
     * @property needNormalized if data should be mapped to (-1,1)
     */
    data class AttributeIndexNormal(val attributeData: VertexAttribute, val vertexIndex:Int,val needNormalized:Boolean)

    fun a() {
//    VertexArrayObject().pushVertexType()
    }
}