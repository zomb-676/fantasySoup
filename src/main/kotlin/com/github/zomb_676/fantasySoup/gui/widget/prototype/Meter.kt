package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Meter(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Meter>(initialInfo) {
    private var pointer: WidgetPicHolder = WidgetPicHolder.empty()
    private var hover: WidgetPicHolder = WidgetPicHolder.empty()

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
        super.contains(texture) || pointer.texture == texture || hover.texture == texture

    override fun contains(file: File): Boolean =
        super.contains(file) || pointer.file == file || hover.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || pointer == widgetPicHolder || hover == widgetPicHolder

    override fun drawComponentWithSelectButtonCore(widgetInfos: OperationStage.WidgetInfos) {
        super.drawComponentWithSelectButtonCore(widgetInfos)
        ImGuiMethods.pushId(2) { drawComponentWithSelectButton("hover", hover, widgetInfos) }
        ImGuiMethods.pushId(3) { drawComponentWithSelectButton("pointer", pointer, widgetInfos) }
    }

    override fun getWidgetPicHolder(texture: Texture): WidgetPicHolder? =
        if (default.texture == texture) default else if (hover.texture == texture) hover else if (pointer.texture == texture) pointer else null

    override fun hasFullComplete(): Boolean = hasRequiredComplete() && hover.isNotEmpty()

    override fun hasRequiredComplete(): Boolean = super.hasRequiredComplete() && pointer.isNotEmpty()
}