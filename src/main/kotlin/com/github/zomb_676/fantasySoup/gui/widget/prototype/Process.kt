package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Process(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Process>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.PROCESS

    override fun merge(another: Process) {

    }
}