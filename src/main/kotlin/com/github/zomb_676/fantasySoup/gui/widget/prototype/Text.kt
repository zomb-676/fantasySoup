package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Text(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Text>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.TEXT

    override fun merge(another: Text) {

    }
}