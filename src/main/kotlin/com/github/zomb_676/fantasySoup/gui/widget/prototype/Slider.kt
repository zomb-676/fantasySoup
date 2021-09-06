package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Slider(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Slider>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.SLIDER

    override fun merge(another: Slider) {

    }
}