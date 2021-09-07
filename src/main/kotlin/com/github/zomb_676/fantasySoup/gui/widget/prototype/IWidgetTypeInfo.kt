package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.WidgetInfos
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

sealed class IWidgetTypeInfo<T : IWidgetTypeInfo<T>>(val initialInfo: OperationStage.WidgetInfoInitObject) {

    abstract fun getWidgetType(): ActualType

    /**
     * return false if merge failed , which means conflict exist
     * remove another from [WidgetInfos]
     */
    abstract fun merge(another: T)

    open fun needMultiPicType():Boolean = false

    open fun contains(texture: Texture): Boolean {
        return initialInfo.texture == texture
    }

    open fun contains(file: File): Boolean {
        return initialInfo.file == file
    }

    open fun contains(picInfo: OperationStage.WidgetInfoInitObject): Boolean {
        return initialInfo == picInfo
    }

    /**
     * wrap in tooltip scope by outside
     */
    open fun drawComponentToolTip() {}

    /**
     * wrap in scope outside
     */
    open fun drawSelectPicTypeInfo(){}
}