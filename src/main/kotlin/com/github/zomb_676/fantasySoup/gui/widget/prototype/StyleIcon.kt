package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class StyleIcon(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<StyleIcon>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.STYLE_ICON

    override fun merge(another: StyleIcon) {

    }
}