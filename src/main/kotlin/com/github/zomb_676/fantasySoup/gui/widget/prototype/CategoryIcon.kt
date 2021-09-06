package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class CategoryIcon : IWidgetTypeInfo<CategoryIcon>() {
    override fun getWidgetType(): ActualType = ActualType.CATEGORY_ICON

    override fun merge(another: CategoryIcon): Boolean {

    }

}