package com.github.zomb_676.fantasySoup.gui

import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * In charge of all draw work in gui
 */
//TODO highest priority
@OnlyIn(Dist.CLIENT)
@Suppress("unused")
class Canvas {
    private var r: Int = 255
    private var g: Int = 255
    private var b: Int = 255
    private var a: Int = 255
    /**
     * Depth
     */
    private var z : Double = 0.0
    private var minecraft = Minecraft.getInstance()
    private var window = minecraft.mainWindow
    private var itemRender = minecraft.itemRenderer

    fun setRGBA(r: Int, g: Int, b: Int, a: Int) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun setColor(color: Int) {
        a = color shr 24 and 0xff
        r = color shr 16 and 0xff
        g = color shr 8 and 0xff
        b = color and 0xff
    }

    fun setAlpha(a: Int) {
        this.a = a
    }

    fun resetColor() {
        r = 255
        g = 255
        b = 255
        a = 255
    }

    fun setZ(z: Double) {
        this.z = z
    }

}