package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Misc(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Misc>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.MISC

    override fun merge(another: Misc) {

    }

}