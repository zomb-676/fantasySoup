package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class OperationIcon : IWidgetTypeInfo<OperationIcon>() {
    override fun getWidgetType(): ActualType = ActualType.OPERATION_ICON

    override fun merge(another: OperationIcon): Boolean {

    }
}