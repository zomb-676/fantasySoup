package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Text : IWidgetTypeInfo<Text>() {
    override fun getWidgetType(): ActualType = ActualType.TEXT

    override fun merge(another: Text): Boolean {

    }
}