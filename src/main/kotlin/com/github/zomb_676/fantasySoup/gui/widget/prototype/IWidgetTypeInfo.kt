package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.WidgetInfos
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

sealed class IWidgetTypeInfo<T : IWidgetTypeInfo<T>>(val initialInfo: OperationStage.WidgetInfoInitObject) {

    companion object{
        var totalIndex = 0
    }

    val index = ++totalIndex

    abstract fun getWidgetType(): ActualType

    var widgetName : String = "${getWidgetType().roughName}:$index"

        /**
     * return false if merge failed , which means conflict exist
     * remove another from [WidgetInfos]
     */
    abstract fun merge(another: T)

    open fun needMultiPicType(): Boolean = false

    open fun contains(texture: Texture): Boolean {
        return initialInfo.texture == texture
    }

    open fun contains(file: File): Boolean {
        return initialInfo.file == file
    }

    open fun contains(picInfo: OperationStage.WidgetInfoInitObject): Boolean {
        return initialInfo == picInfo
    }

    open fun drawComponentInfo() {}

    /**
     * wrap in scope outside
     */
    open fun drawSelectPicTypeInfo() {}
}