package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class MultiSlot(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<MultiSlot>(initialInfo) {

    private var hover : WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.MULTI_SLOT

    override fun merge(another: MultiSlot) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || hover?.texture == texture


    override fun contains(file: File): Boolean =
        super.contains(file) || hover?.file == file


    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || hover == widgetPicHolder
}