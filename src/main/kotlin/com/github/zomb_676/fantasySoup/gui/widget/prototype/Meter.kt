package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Meter(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Meter>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.METER

    override fun merge(another: Meter) {

    }
}