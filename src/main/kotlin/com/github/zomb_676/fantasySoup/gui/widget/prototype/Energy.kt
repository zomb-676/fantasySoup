package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Energy(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Energy>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.ENERGY

    override fun merge(another: Energy) {

    }
}