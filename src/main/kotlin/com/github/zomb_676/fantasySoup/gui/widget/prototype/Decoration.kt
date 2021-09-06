package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Decoration : IWidgetTypeInfo<Decoration>() {
    override fun getWidgetType(): ActualType = ActualType.DECORATIONS

    override fun merge(another: Decoration): Boolean {

    }
}