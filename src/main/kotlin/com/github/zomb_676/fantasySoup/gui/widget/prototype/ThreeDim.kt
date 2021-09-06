package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class ThreeDim : IWidgetTypeInfo<ThreeDim>() {
    override fun getWidgetType(): ActualType = ActualType.THREE_DIM

    override fun merge(another: ThreeDim): Boolean {

    }
}