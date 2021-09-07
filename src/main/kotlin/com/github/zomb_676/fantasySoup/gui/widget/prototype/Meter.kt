package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Meter(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Meter>(initialInfo) {

    private var pointer: WidgetPicHolder? = null
    private var hover: WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.METER

    override fun merge(another: Meter) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("pointer", pointer)
        drawComponent("hover", hover)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || pointer?.texture == texture || hover?.texture == texture


    override fun contains(file: File): Boolean =
        super.contains(file) || pointer?.file == file || hover?.file == file


    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || pointer == widgetPicHolder || hover == widgetPicHolder
}