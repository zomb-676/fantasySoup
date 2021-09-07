package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture

class NeedLoadStage : OperationStage() {
    var data: List<PicInfo>? = null
    override fun draw() {
        wrapImGUIObject {
            window("WidgetOperationPanel-NeedLoading") {
                if (data == null) {
                    button("Load Fixed") {
                        val files = WidgetOperationPanel.loadAllTextureSelfRaw()
                        data = listOf(* files.map { PicInfo(it, FileTexture(it.path).genTexture()) }.toTypedArray())
                    }
                }
            }
        }
    }

    override fun tick(): OperationStage? =
        if (data == null) null else ManualSpecifyStage(data!!)

}