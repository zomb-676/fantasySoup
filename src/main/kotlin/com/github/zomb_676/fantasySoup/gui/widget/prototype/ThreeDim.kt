package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class ThreeDim(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<ThreeDim>(initialInfo) {

    private val hover : WidgetPicHolder? = null
    private val interact : WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.THREE_DIM

    override fun merge(another: ThreeDim) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
        drawComponent("interact", interact)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || hover?.texture == texture || interact?.texture == texture


    override fun contains(file: File): Boolean =
        super.contains(file) || hover?.file == file || interact?.file == file


    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || hover == widgetPicHolder || interact == widgetPicHolder
}