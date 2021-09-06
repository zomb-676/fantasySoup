package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Energy : IWidgetTypeInfo<Energy>() {
    override fun getWidgetType(): ActualType = ActualType.ENERGY

    override fun merge(another: Energy): Boolean {

    }
}