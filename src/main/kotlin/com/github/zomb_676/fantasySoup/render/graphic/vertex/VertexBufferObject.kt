package com.github.zomb_676.fantasySoup.render.graphic.vertex

import com.github.zomb_676.fantasySoup.render.graphic.Constants
import com.github.zomb_676.fantasySoup.render.graphic.Constants.VertexStorageType
import org.lwjgl.opengl.GL43

class VertexBufferObject(private val vertexStorageType: VertexStorageType) {
    private var vertexBufferObjectId = -1

    fun genVertexBufferObject(): VertexBufferObject {
        vertexBufferObjectId = GL43.glGenBuffers()
        return this
    }

    fun bindVertexBufferObject(): VertexBufferObject {
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER,vertexBufferObjectId)
        return this
    }

    fun bindDate(floatArray: FloatArray): VertexBufferObject {
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER,floatArray,vertexStorageType.type)
        return this
    }
}