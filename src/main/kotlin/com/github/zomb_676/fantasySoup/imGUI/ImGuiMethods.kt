package com.github.zomb_676.fantasySoup.imGUI

import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.utils.rough
import com.github.zomb_676.fantasySoup.utils.takeIfTrue
import imgui.ImFont
import imgui.ImGui
import imgui.extension.imnodes.ImNodes
import imgui.extension.imnodes.flag.ImNodesAttributeFlags
import imgui.extension.imnodes.flag.ImNodesPinShape
import imgui.flag.ImGuiDir
import org.intellij.lang.annotations.MagicConstant

object ImGuiMethods {

    fun sameLine() = ImGui.sameLine()

    fun sameLine(startX: Float) = ImGui.sameLine(startX)

    inline fun wrapImGUIObject(codeBlock: ImGuiMethods.() -> Unit) {
        codeBlock(ImGuiMethods)
    }

    /**
     * don't nest this
     */
    inline fun window(windowName: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.begin(windowName)
        codeBlock(ImGuiMethods)
        ImGui.end()
    }

    /**
     * don't nest this
     */
    inline fun window(windowName: String, imGuiWindowFlags: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.begin(windowName, imGuiWindowFlags)
        codeBlock(ImGuiMethods)
        ImGui.end()
    }

    inline fun area(strID: String, width: Float, height: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginChild(strID, width, height)
        codeBlock(ImGuiMethods)
        ImGui.endChild()
    }

    fun bulletText(text: String) = ImGui.bulletText(text)

    /**
     * @param codeBlock invoke when button is released
     */
    fun button(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.button(name).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    /**
     * @param codeBlock invoke when button is released
     */
    fun button(name: String, width: Float, height: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.button(name, width, height).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    /**
     * @param codeBlock invoke when button is released
     * @param dir arrow direction , see [ImGuiDir]
     */
    fun arrowButton(
        name: String,
        @MagicConstant(valuesFromClass = ImGuiDir::class) dir: Int,
        codeBlock: ImGuiMethods.() -> Unit
    ) {
        ImGui.arrowButton(name, dir).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    fun image(textureId: Int, sizeX: Float, sizeY: Float) {
        ImGui.image(textureId, sizeX, sizeY)
    }

    fun image(texture: Texture, sizeX: Float, sizeY: Float) {
        ImGui.image(texture.textureID, sizeX, sizeY)
    }

    fun imageFlip(textureID: Int, sizeX: Float, sizeY: Float) {
        ImGui.image(textureID, sizeX, sizeY, 0f, 1f, 1f, 0f)
    }

    fun imageFlip(texture: Texture, sizeX: Float, sizeY: Float) {
        ImGui.image(texture.textureID, sizeX, sizeY, 0f, 1f, 1f, 0f)
    }

    fun imageFlip(texture: Texture) {
        ImGui.image(texture.textureID, texture.width.toFloat(), texture.height.toFloat(), 0f, 1f, 1f, 0f)
    }

    inline fun imageButton(textureId: Int, sizeX: Float, sizeY: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.imageButton(textureId, sizeX, sizeY).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun imageButton(texture: Texture, sizeX: Float, sizeY: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.imageButton(texture.textureID, sizeX, sizeY).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun imageFlipButton(textureID: Int, sizeX: Float, sizeY: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.imageButton(textureID, sizeX, sizeY, 0f, 1f, 1f, 0f).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun imageFlipButton(texture: Texture, sizeX: Float, sizeY: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.imageButton(texture.textureID, sizeX, sizeY, 0f, 1f, 1f, 0f).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun checkBox(name: String, isActive: Boolean, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.checkbox(name, isActive).takeIfTrue {
            codeBlock(ImGuiMethods)

        }
    }

    inline fun radioButton(name: String, isActive: Boolean, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.radioButton(name, isActive).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    fun progressBar(friction: Float) =
        ImGui.progressBar(friction)


    fun progressBar(friction: Float, sizeArgX: Float, sizeArgY: Float) =
        ImGui.progressBar(friction, sizeArgX, sizeArgY)


    fun progressBar(friction: Float, sizeArgX: Float, sizeArgY: Float, overlay: String) =
        ImGui.progressBar(friction, sizeArgX, sizeArgY, overlay)

    fun bullet() = ImGui.bullet()

    fun bullet(num: Int) = repeat(num) { ImGui.bullet() }

    inline fun combo(name: String, previewValue: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginCombo(name, previewValue).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endCombo()
        }
    }

    inline fun node(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.treeNode(name).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.treePop()
        }
    }

    inline fun collapsingHeader(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.collapsingHeader(name).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun listBox(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginListBox(name).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endListBox()
        }
    }

    inline fun menuBar(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginMenuBar().takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endMenuBar()
        }
    }

    inline fun menu(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginMenu(name).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endMenu()
        }
    }

    inline fun menuItem(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.menuItem(name).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun menuItem(name: String, shortcut: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.menuItem(name, shortcut).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun menuItem(name: String, shortcut: String, selected: Boolean, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.menuItem(name, shortcut, selected).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun menuItem(
        name: String,
        shortcut: String,
        selected: Boolean,
        enabled: Boolean,
        codeBlock: ImGuiMethods.() -> Unit
    ) {
        ImGui.menuItem(name, shortcut, selected, enabled).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }


    inline fun tooltip(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginTooltip()
        codeBlock(ImGuiMethods)
        ImGui.endTooltip()
    }

    inline fun tooltipHover(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.isItemHovered().takeIfTrue {
            ImGui.beginTooltip()
            codeBlock(ImGuiMethods)
            ImGui.endTooltip()
        }
    }

    inline fun tooltipHover(itemBlock: (ImGuiMethods) -> Unit, codeBlock: ImGuiMethods.() -> Unit) {
        itemBlock(ImGuiMethods)
        tooltipHover(codeBlock)
    }

    /**
     * use [tabItem] in this scope
     */
    inline fun tab(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginTabBar(name).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endTabBar()
        }
    }

    /**
     * used in scope of [tab]
     */
    inline fun tabItem(name: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginTabItem(name).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endTabItem()
        }
    }

    /**
     * use [tableItem] in this scope
     *
     * table use see [https://github.com/ocornut/imgui/issues/3740#issuecomment-764882290]
     */
    inline fun table(tableID: String, coloumNum: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginTable(tableID, coloumNum).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endTable()
        }
    }

    inline fun tableItem(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.tableNextColumn()
        codeBlock(ImGuiMethods)
    }

    fun tableHeader(headerName: String) {
        tableItem { ImGui.tableHeader(headerName) }
    }

    inline fun indent(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.indent()
        codeBlock(ImGuiMethods)
        ImGui.unindent()
    }

    inline fun indent(indentWidth: Float, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.indent(indentWidth)
        codeBlock(ImGuiMethods)
        ImGui.unindent(indentWidth)
    }

    @Throws(IllegalArgumentException::class)
    fun getSystemFontDir(): String {
        val systemName = System.getProperty("os.name").rough()
        return if ("windows" in systemName) {
            "C:/Windows/Fonts/"
        } else if ("linux" in systemName) {
            "/usr/share/fonts"
        } else {
            throw IllegalArgumentException("can't get font file form os $systemName")
        }
    }

    fun getFontFromSysTTFDir(ttfName: String): ImFont {
        val imGuiIO = ImGui.getIO()
        val font = imGuiIO.fonts.addFontFromFileTTF(getSystemFontDir() + ttfName, 20f)
        IImGUI.State.imGuiGl3.updateFontsTexture()
        return font
    }

    inline fun leftClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.isItemClicked(0).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun rightClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.isItemClicked(1).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun middleClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.isItemClicked(2).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun doubleLeftClickClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0)).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun doubleRightClickClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(1)).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    inline fun doubleMiddleClickClickLast(codeBlock: ImGuiMethods.() -> Unit) {
        (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(2)).takeIfTrue {
            codeBlock(ImGuiMethods)
        }
    }

    fun setPopupEnable(strID: String) = ImGui.openPopup(strID)

    /**
     * use with [ImGui.openPopup] or [setPopupEnable]
     */
    inline fun popup(strID: String, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.beginPopup(strID).takeIfTrue {
            codeBlock(ImGuiMethods)
            ImGui.endPopup()
        }
    }

    fun text(text: String) {
        ImGui.text(text)
    }

    fun coloredText(text: String, color: Int) {
        ImGui.textColored(color, text)
    }

    fun coloredText(text: String, red: Int, green: Int, blue: Int, alpha: Int) {
        ImGui.textColored(red, green, blue, alpha, text)
    }

    fun coloredText(text: String, red: Float, green: Float, blue: Float, alpha: Float) {
        ImGui.textColored(red, green, blue, alpha, text)
    }

    inline fun pushId(id: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImGui.pushID(id)
        codeBlock(ImGuiMethods)
        ImGui.popID()
    }

    //extension for https://github.com/Nelarius/imnodes

    inline fun nodeEditor(codeBlock: ImGuiMethods.() -> Unit) {
        ImNodes.beginNodeEditor()
        codeBlock(ImGuiMethods)
        ImNodes.endNodeEditor()
    }

    inline fun node(hardcodeId: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImNodes.beginNode(hardcodeId)
        codeBlock(ImGuiMethods)
        ImNodes.endNode()
    }

    inline fun inputAttribute(hardcodeId: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImNodes.beginInputAttribute(hardcodeId)
        codeBlock(ImGuiMethods)
        ImNodes.endInputAttribute()
    }

    inline fun inputAttribute(
        hardcodeId: Int,
        @MagicConstant(valuesFromClass = ImNodesPinShape::class) imNodePinShape: Int,
        codeBlock: ImGuiMethods.() -> Unit
    ) {
        ImNodes.beginInputAttribute(hardcodeId, imNodePinShape)
        codeBlock(ImGuiMethods)
        ImNodes.endInputAttribute()
    }

    inline fun outputAttribute(hardcodeId: Int, codeBlock: ImGuiMethods.() -> Unit) {
        ImNodes.beginOutputAttribute(hardcodeId)
        codeBlock(ImGuiMethods)
        ImNodes.endOutputAttribute()
    }

    inline fun outputAttribute(
        hardcodeId: Int,
        @MagicConstant(valuesFromClass = ImNodesPinShape::class) imNodePinShape: Int,
        codeBlock: ImGuiMethods.() -> Unit
    ) {
        ImNodes.beginOutputAttribute(hardcodeId, imNodePinShape)
        codeBlock(ImGuiMethods)
        ImNodes.endOutputAttribute()
    }

    inline fun attributeFlag(
        @MagicConstant(valuesFromClass = ImNodesAttributeFlags::class) imNodesAttributeFlags: Int,
        codeBlock: ImGuiMethods.() -> Unit
    ) {
        ImNodes.pushAttributeFlag(imNodesAttributeFlags)
        codeBlock(ImGuiMethods)
        ImNodes.endOutputAttribute()
    }

    inline fun nodeTitle(codeBlock: ImGuiMethods.() -> Unit){
        ImNodes.beginNodeTitleBar()
        codeBlock(ImGuiMethods)
        ImNodes.endNodeTitleBar()
    }
}