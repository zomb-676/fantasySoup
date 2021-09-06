package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class MultiSlot : IWidgetTypeInfo<MultiSlot>() {
    override fun getWidgetType(): ActualType = ActualType.MULTI_SLOT

    override fun merge(another: MultiSlot): Boolean {

    }
}