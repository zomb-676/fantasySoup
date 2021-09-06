package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Slider : IWidgetTypeInfo<Slider>() {
    override fun getWidgetType(): ActualType = ActualType.SLIDER

    override fun merge(another: Slider): Boolean {

    }
}