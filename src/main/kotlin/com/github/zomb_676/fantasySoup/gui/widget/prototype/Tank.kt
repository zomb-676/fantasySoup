package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Tank(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Tank>(initialInfo) {
        override fun getWidgetType(): ActualType = ActualType.TANK

    override fun merge(another: Tank) {

    }
}