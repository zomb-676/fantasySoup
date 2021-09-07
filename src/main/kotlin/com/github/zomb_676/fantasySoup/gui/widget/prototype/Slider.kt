package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Slider(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Slider>(initialInfo) {

    private var hover : WidgetPicHolder? = null
    private var bar : WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.SLIDER

    override fun merge(another: Slider) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
        drawComponent("bar", bar)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || hover?.texture == texture || bar?.texture == texture


    override fun contains(file: File): Boolean =
        super.contains(file) || hover?.file == file || bar?.file == file


    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || hover == widgetPicHolder || bar == widgetPicHolder
}