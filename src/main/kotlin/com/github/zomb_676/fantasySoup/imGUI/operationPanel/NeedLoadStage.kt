package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture
import imgui.ImGui
import java.io.File

class NeedLoadStage : OperationStage() {
    var data = mutableListOf<File>()
    var result = mutableListOf<PicInfo>()
    private var loadProcess = 0
    private var processTotal: Int = -1
    private var loadComplete = false
    override fun draw() { //ugly
        wrapImGUIObject {
            window("WidgetOperationPanel-NeedLoading") {
                if (!loadComplete) {
                    button("Load Fixed") {
                        if (processTotal==-1){
                            data.addAll(WidgetOperationPanel.loadAllTextureSelfRaw())
                            processTotal = data.size
                        }
                    }
                    if (loadProcess != 0) {
                        progressBar(
                            loadProcess.toFloat().plus(1) / processTotal.plus(1), ImGui.getWindowWidth(), ImGui.getFontSize().toFloat(),
                            "load process ${loadProcess.plus(1)}/${processTotal.plus(1)}"
                        )
                        text("loading ${data[loadProcess].name}")
                        (loadProcess-1 downTo 0).forEach {
                            text("loaded ${data[it].name}")
                        }
                    }
                    if (loadProcess != processTotal && processTotal != -1) {
                        data[loadProcess].apply { result.add(PicInfo(this,FileTexture(this.path))) }
                        loadProcess++
                    }
                    if (loadProcess == processTotal) {
                        loadComplete = true
                    }
                }
            }
        }
    }

    override fun tick(): OperationStage? =
        if (loadComplete) ManualSpecifyStage(result) else null
}