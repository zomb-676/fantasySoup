package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Tank : IWidgetTypeInfo<Tank>() {
        override fun getWidgetType(): ActualType = ActualType.TANK

    override fun merge(another: Tank): Boolean {

    }
}