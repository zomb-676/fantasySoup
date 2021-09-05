package com.github.zomb_676.fantasySoup.imGUI

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL43
import kotlin.concurrent.thread

object IImGUI {

    @JvmStatic
    fun main(args: Array<String>) {
        initWithGL(100, 200)
        wrapShouldClose { wrapRunWithGL {
                window("rui") {
                    tab("tab1") {
                        tabItem("item1") {
                            button("thonk") {}
                        }
                        tabItem("item2") {
                            button("thonk2") {}
                        }
                    }
                    listBox("box"){
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                        ImGui.text("thonk")
                    }
                    ImGui.radioButton("thonk", true)
                    ImGui.radioButton("thonk", false)

                    tooltipHover({ ImGui.text("tooltip2") }, {
                        ImGui.beginTooltip()
                        ImGui.text("tooltip")
                        ImGui.text("tooltip1")
                        ImGui.endTooltip()
                    })
                }
            }
        }
        disposeWithGl()
    }

    object State {
        val imGuiGlfw = ImGuiImplGlfw()
        val imGuiGl3 = ImGuiImplGl3()
        var window: Long = -1
    }

    inline fun wrapShouldCloseNewThread(window: Long = State.window, crossinline codeBlock: ImGuiMethods.() -> Unit) {
        thread { wrapShouldClose(window,codeBlock)}.start()
    }

    inline fun wrapShouldClose(window: Long = State.window, codeBlock: ImGuiMethods.() -> Unit) {
        while (!GLFW.glfwWindowShouldClose(window)) {
            codeBlock(ImGuiMethods)
        }
    }

    fun initFromOther(window: Long) {
        State.window = window
        //init ImGUI
        ImGui.createContext()
        State.imGuiGlfw.init(State.window, true)
        State.imGuiGl3.init()
    }

    @Throws(IllegalStateException::class)
    fun initWithGL(width: Int, height: Int) {
        //init glfw
        GLFWErrorCallback.createPrint(System.err).set()
        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }
        //decide glfw version
        State.window = GLFW.glfwCreateWindow(width, height, "Test", 0, 0)

        GLFW.glfwMakeContextCurrent(State.window)

        GL.createCapabilities()

        //init ImGUI
        ImGui.createContext()
        State.imGuiGlfw.init(State.window, true)
        State.imGuiGl3.init()

    }

    inline fun wrapRunImGUIOnly(codeBlock: ImGuiMethods.() -> Unit) {
        State.imGuiGlfw.newFrame()
        ImGui.newFrame()

        codeBlock(ImGuiMethods)

        ImGui.render()
        State.imGuiGl3.renderDrawData(ImGui.getDrawData())
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPtr = GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()
            GLFW.glfwMakeContextCurrent(backupWindowPtr)
        }
    }

    inline fun wrapRunWithGL(codeBlock: ImGuiMethods.() -> Unit) {
        GL43.glClear(GL43.GL_COLOR_BUFFER_BIT)
        State.imGuiGlfw.newFrame()
        ImGui.newFrame()

        codeBlock(ImGuiMethods)

        ImGui.render()
        State.imGuiGl3.renderDrawData(ImGui.getDrawData())
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPtr = GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()
            GLFW.glfwMakeContextCurrent(backupWindowPtr)
        }

        GLFW.glfwSwapBuffers(State.window)
        GLFW.glfwPollEvents()
    }

    fun disposeWithGl() {
        //dispose ImGUI
        State.imGuiGlfw.dispose()
        State.imGuiGl3.dispose()
        ImGui.destroyContext()
        //dispose GLFW
        Callbacks.glfwFreeCallbacks(State.window)
        GLFW.glfwDestroyWindow(State.window)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    fun disposeImGUIOnly() {
        //dispose GLFW
        Callbacks.glfwFreeCallbacks(State.window)
        GLFW.glfwDestroyWindow(State.window)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

}