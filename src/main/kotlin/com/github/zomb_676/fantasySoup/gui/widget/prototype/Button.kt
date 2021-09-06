package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Button : IWidgetTypeInfo<Button>() {
    override fun getWidgetType(): ActualType = ActualType.BUTTON

    override fun merge(another: Button): Boolean {

    }
}