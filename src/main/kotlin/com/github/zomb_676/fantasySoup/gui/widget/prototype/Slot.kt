package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Slot : IWidgetTypeInfo<Slot>() {
    override fun getWidgetType(): ActualType = ActualType.SLOT

    override fun merge(another: Slot): Boolean {

    }
}