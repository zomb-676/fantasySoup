package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Button(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Button>(initialInfo) {
    private val hover: WidgetPicHolder = WidgetPicHolder.empty()
    private val pressed: WidgetPicHolder = WidgetPicHolder.empty()

    override fun getWidgetType(): ActualType = ActualType.BUTTON

    override fun needMultiPicType(): Boolean = true

    override fun merge(another: Button) {

    }

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
        drawComponent("pressed", pressed)
    }

    override fun hasFullComplete(): Boolean = super.hasFullComplete() && hover.isNotEmpty() && pressed.isNotEmpty()

    override fun contains(texture: Texture): Boolean =
        default.texture == texture || hover.texture == texture || pressed.texture == texture

    override fun contains(file: File): Boolean =
        default.file == file || hover.file == file || pressed.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        default == widgetPicHolder || hover == widgetPicHolder || pressed == widgetPicHolder

    override fun drawComponentWithSelectButtonCore(widgetInfos: OperationStage.WidgetInfos) {
        super.drawComponentWithSelectButtonCore(widgetInfos)
        ImGuiMethods.pushId(2) { drawComponentWithSelectButton("hover", hover, widgetInfos) }
        ImGuiMethods.pushId(3) { drawComponentWithSelectButton("pressed", pressed, widgetInfos) }
    }

    override fun getWidgetPicHolder(texture: Texture): WidgetPicHolder? =
        if (default.texture == texture) default else if (hover.texture == texture) hover else if (pressed.texture == texture) pressed else null
}