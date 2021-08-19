package com.github.zomb_676.fantasySoup.render.graphic.vertex

import org.lwjgl.opengl.GL43

/**
 * @property bindingIndex
 * @property arrayID the vertex data ID
 * @property vertexIndexCount Self-increasing with the push of VertexAttribute
 */
class VertexArrayObject {
    private var vertexArrayObjectId = -1
    private val types = mutableListOf<AttributeIndex>()
    private val bindingIndex = bindingIndexTotal++
    private val arrayID = 0
    private var vertexIndexCount = 0

    companion object{
        private var bindingIndexTotal = 0
    }

    fun genVertexArrayObject() {
        vertexArrayObjectId = GL43.glGenVertexArrays()
    }

    fun pushVertexType(vertexAttribute: VertexAttribute) {
        types.add(AttributeIndex(vertexAttribute,vertexIndexCount++))
    }

    fun bindVertexArrayObject(){
        GL43.glBindVertexArray(vertexArrayObjectId)
    }

    fun setup(data:FloatArray){
        this.bindVertexArrayObject()
        var stride = 0
        for (attributeIndexed in types){
            val vertexIndex = attributeIndexed.vertexIndex
            val data = attributeIndexed.attribute
            GL43.glEnableVertexAttribArray(vertexIndex)
            GL43.glVertexAttribFormat(vertexIndex,data.vertexDataType.count
                ,data.vertexDataType.type.type,false,stride)
            GL43.glVertexAttribBinding(vertexIndex,bindingIndex)
            stride+=data.vertexDataType.size
        }
        GL43.glBindVertexBuffer(bindingIndex,arrayID,0,stride)
        endUse()
    }

    fun endUse(){
        GL43.glBindVertexArray(0)
        (0 until types.size).forEach { GL43.glDisableVertexAttribArray(it) }
    }

    /**
     * @param vertexIndex inconsistent with layout (location = num)
     */
    data class AttributeIndex(val attribute: VertexAttribute,val vertexIndex:Int)

    fun a() {
//    VertexArrayObject().pushVertexType()
    }
}