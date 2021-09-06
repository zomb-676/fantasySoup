package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Input(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Input>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.INPUT

    override fun merge(another: Input) {

    }
}