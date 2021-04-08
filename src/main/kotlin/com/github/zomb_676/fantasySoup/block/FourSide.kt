package com.github.zomb_676.fantasySoup.block

import net.minecraft.state.EnumProperty
import net.minecraft.util.IStringSerializable

enum class FourSide(private val pos: String) : IStringSerializable {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");

    override fun toString(): String {
        return this.string
    }

    override fun getString(): String {
        return pos
    }

    companion object {
        val fourSides: EnumProperty<FourSide> = EnumProperty
            .create("side", FourSide::class.java, UP, DOWN, LEFT, RIGHT)
    }
}