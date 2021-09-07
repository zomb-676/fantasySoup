package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import imgui.ImGui

object AllWidgetPanel {
        fun draw(widgetInfos: OperationStage.WidgetInfos) {
            if (widgetInfos.length() == 0) return
            wrapImGUIObject {
                window("GeneratedWidgets") {
                    for ((type, container) in widgetInfos.container) {
                        if (container.isEmpty()) continue
                        var index = 1
                        table(type.name, 2) {
                            tableHeader("widgetName")
                            tableHeader(type.roughName)
                            for (widgetInfo in container) {
                                val (file, texture) = widgetInfo.initialInfo
                                tableItem { ImGui.text("${type.roughName}:${index++}") }
                                tooltipHover {
                                    ImGui.text("name:${file.name}")
                                    imageFlip(texture.textureID, texture.width.toFloat(), texture.height.toFloat())
                                }
                                tableItem { ImGui.text(type.name) }
                            }
                        }
                    }
                }
            }
        }
    }