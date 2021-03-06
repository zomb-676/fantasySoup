package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Input(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Input>(initialInfo) {
    private var hover: WidgetPicHolder = WidgetPicHolder.empty()
    private var interact: WidgetPicHolder = WidgetPicHolder.empty()

    override fun getWidgetType(): ActualType = ActualType.INPUT

    override fun merge(another: Input) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
        drawComponent("interact", interact)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || hover.texture == texture || interact.texture == texture

    override fun contains(file: File): Boolean =
        super.contains(file) || hover.file == file || interact.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || hover == widgetPicHolder || interact == widgetPicHolder

    override fun drawComponentWithSelectButtonCore(widgetInfos: OperationStage.WidgetInfos) {
        super.drawComponentWithSelectButtonCore(widgetInfos)
        ImGuiMethods.pushId(2) { drawComponentWithSelectButton("hover", hover, widgetInfos) }
        ImGuiMethods.pushId(3) { drawComponentWithSelectButton("interact", interact, widgetInfos) }
    }

    override fun getWidgetPicHolder(texture: Texture): WidgetPicHolder? =
        if (default.texture == texture) default else if (hover.texture == texture) hover else if (interact.texture == texture) interact else null

    override fun hasFullComplete(): Boolean = super.hasFullComplete() && hover.isNotEmpty() && interact.isNotEmpty()
}