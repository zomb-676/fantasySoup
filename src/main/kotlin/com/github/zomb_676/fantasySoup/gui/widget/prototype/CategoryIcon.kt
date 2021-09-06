package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class CategoryIcon(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<CategoryIcon>(initialInfo) {
    override fun getWidgetType(): ActualType = ActualType.CATEGORY_ICON

    override fun merge(another: CategoryIcon) {

    }

}