package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Misc : IWidgetTypeInfo<Misc>() {
    override fun getWidgetType(): ActualType = ActualType.MISC

    override fun merge(another: Misc): Boolean {

    }

}