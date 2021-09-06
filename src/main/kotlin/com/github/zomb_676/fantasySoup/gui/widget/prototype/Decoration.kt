package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Decoration(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Decoration>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.DECORATIONS

    override fun merge(another: Decoration) {

    }
}