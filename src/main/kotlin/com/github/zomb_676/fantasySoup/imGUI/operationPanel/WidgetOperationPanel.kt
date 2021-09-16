package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.IImGUI
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.node.Node
import com.github.zomb_676.fantasySoup.imGUI.node.Vertex
import com.github.zomb_676.fantasySoup.utils.assert
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
import java.io.File

object WidgetOperationPanel {

    val space = '\u200C'
    var currentStage: OperationStage = NeedLoadStage()

    fun spaceN(n: Int): String {
        var base = ""
        repeat(n) {
            base += space
        }
        return base
    }

    /**
     * independent launch
     */
    @JvmStatic
    fun main(args: Array<String>) {
        IImGUI.initWithGL(1600, 900)
        val font = ImGuiMethods.getFontFromSysTTFDir("comic.ttf")
        var hasLinked = false
        var node = Node("input")
            .addInput(Vertex.InputVertex("in"))
            .addOutput(Vertex.OutputVertex("out"))
        IImGUI.wrapShouldClose {
            IImGUI.wrapRunWithGL {
                pushFont(font) {
                    currentStage.tick().takeIfNotNull { currentStage = it }
                    currentStage.draw()
                }
//                ImGui.pushStyleColor(ImGuiCol.CheckMark,1f,0f,0f,5f)
//                ImGui.pushStyleColor(ImGuiCol.BorderShadow,)
//                ImGui.pushStyleColor(ImGuiCol.Border,)
//                ImGui.radioButton()
//                ImGui.popStyleColor(2)
//                window("node editor") {
//                    nodeEditor {
//                        node.draw()
//                    }
//                }

            }

        }
        IImGUI.disposeWithGl()
    }

    /**
     * init gl by third party
     */
    fun launchFromThirdParty(window: Long): () -> Unit {
        IImGUI.initFromOther(window)
        return {
            IImGUI.wrapRunImGUIOnly {

            }
        }
    }

    fun loadAllTextureSelfRaw(): List<File> {
        val path = File("src/main/resources/assets/fantasy_soup/textures/gui/line_style").assert { isDirectory }
        return path.listFiles()!!.filter { it.extension == "png" }
    }

}