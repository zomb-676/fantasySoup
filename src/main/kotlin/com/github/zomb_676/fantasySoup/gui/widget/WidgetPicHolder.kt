package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

data class WidgetPicHolder(var file: File?, var texture: Texture?) {

    companion object {
        fun empty() = WidgetPicHolder(null, null)
    }

    fun set(file: File,texture: Texture) {
        this.file = file
        this.texture = texture
    }

    fun set(picInfo: OperationStage.PicInfo) {
        this.file = picInfo.file
        this.texture = picInfo.texture
    }

    fun isEmpty() = file == null || texture == null

    fun isNotEmpty() = file != null && texture != null

    fun clear() {
        file = null
        texture = null
    }

    inline fun takeIfNotEmpty(codeBlock : (WidgetPicHolder)->Unit) {
        if (isNotEmpty()){
            codeBlock(this)
        }
    }

    constructor(initObject: OperationStage.WidgetInfoInitObject) : this(initObject.file, initObject.texture)
}