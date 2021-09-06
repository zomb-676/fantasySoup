package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class CheckBox : IWidgetTypeInfo<CheckBox>() {
    override fun getWidgetType(): ActualType = ActualType.CHECKBOX

    override fun merge(another: CheckBox): Boolean {
        TODO("Not yet implemented")
    }
}