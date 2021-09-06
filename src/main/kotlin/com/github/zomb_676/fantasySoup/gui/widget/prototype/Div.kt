package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Div : IWidgetTypeInfo<Div>() {
    override fun getWidgetType(): ActualType = ActualType.DIV

    override fun merge(another: Div): Boolean {

    }
}