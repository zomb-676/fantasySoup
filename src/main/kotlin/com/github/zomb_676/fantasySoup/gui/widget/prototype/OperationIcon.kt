package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class OperationIcon(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<OperationIcon>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.OPERATION_ICON

    override fun merge(another: OperationIcon) {

    }
}