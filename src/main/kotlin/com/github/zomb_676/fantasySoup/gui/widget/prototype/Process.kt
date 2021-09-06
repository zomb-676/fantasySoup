package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType

class Process : IWidgetTypeInfo<Process>() {
    override fun getWidgetType(): ActualType = ActualType.PROCESS

    override fun merge(another: Process): Boolean {

    }
}