package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Input : IWidgetTypeInfo<Input>() {
    override fun getWidgetType(): ActualType = ActualType.INPUT

    override fun merge(another: Input): Boolean {

    }
}