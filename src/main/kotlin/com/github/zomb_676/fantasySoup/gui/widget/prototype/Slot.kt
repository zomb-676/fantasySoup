package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Slot(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Slot>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.SLOT

    override fun merge(another: Slot) {

    }
}