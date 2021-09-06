package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class ThreeDim(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<ThreeDim>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.THREE_DIM

    override fun merge(another: ThreeDim) {

    }
}