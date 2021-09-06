package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Button(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Button>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.BUTTON

    override fun merge(another: Button) {

    }
}