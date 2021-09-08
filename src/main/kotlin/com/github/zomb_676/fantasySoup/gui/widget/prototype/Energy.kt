package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Energy(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Energy>(initialInfo) {
    private val fill: WidgetPicHolder = WidgetPicHolder.empty()
    private val hover: WidgetPicHolder = WidgetPicHolder.empty()

    override fun getWidgetType(): ActualType = ActualType.ENERGY

    override fun merge(another: Energy) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("fill", fill)
        drawComponent("hover", hover)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || fill.texture == texture || hover.texture == texture

    override fun contains(file: File): Boolean =
        super.contains(file) || fill.file == file || hover.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || fill == widgetPicHolder || hover == widgetPicHolder

    override fun drawComponentWithSelectButtonCore(widgetInfos: OperationStage.WidgetInfos) {
        super.drawComponentWithSelectButtonCore(widgetInfos)
        ImGuiMethods.pushId(2) { drawComponentWithSelectButton("hover", hover, widgetInfos) }
        ImGuiMethods.pushId(3) { drawComponentWithSelectButton("fill", fill, widgetInfos) }
    }

    override fun getWidgetPicHolder(texture: Texture): WidgetPicHolder? =
        if (default.texture == texture) default else if (hover.texture == texture) hover else if (fill.texture == texture) fill else null
}