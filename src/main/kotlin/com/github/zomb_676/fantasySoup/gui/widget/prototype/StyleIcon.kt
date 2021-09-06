package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class StyleIcon : IWidgetTypeInfo<StyleIcon>() {
    override fun getWidgetType(): ActualType = ActualType.STYLE_ICON

    override fun merge(another: StyleIcon): Boolean {

    }
}