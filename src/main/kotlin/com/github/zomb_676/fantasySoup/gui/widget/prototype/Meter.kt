package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Meter : IWidgetTypeInfo<Meter>() {
    override fun getWidgetType(): ActualType = ActualType.METER

    override fun merge(another: Meter): Boolean {

    }
}