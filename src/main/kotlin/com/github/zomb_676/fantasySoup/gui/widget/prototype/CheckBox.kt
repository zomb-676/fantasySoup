package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class CheckBox(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<CheckBox>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.CHECKBOX

    override fun merge(another: CheckBox) {
        TODO("Not yet implemented")
    }
}