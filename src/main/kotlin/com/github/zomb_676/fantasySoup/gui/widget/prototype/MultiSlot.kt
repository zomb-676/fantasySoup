package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class MultiSlot(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<MultiSlot>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.MULTI_SLOT

    override fun merge(another: MultiSlot) {

    }
}