package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Div(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Div>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.DIV

    override fun merge(another: Div) {

    }
}