package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.WidgetInfos

sealed class IWidgetTypeInfo<T:IWidgetTypeInfo<T>> {

    abstract fun getWidgetType(): ActualType

    /**
     * return false if merge failed , which means conflict exist
     * remove another from [WidgetInfos]
     */
    abstract fun merge(another : T):Boolean

}