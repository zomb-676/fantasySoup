package com.github.zomb_676.fantasySoup.block

import net.minecraft.state.EnumProperty
import net.minecraft.util.IStringSerializable

enum class Vertical(private val pos: String) : IStringSerializable {
    UP("up"),
    SAME("same"),
    DOWN("down");

    override fun toString(): String {
        return this.string
    }

    override fun getString(): String {
        return pos
    }

    companion object{
        val verticalRelation: EnumProperty<Vertical> = EnumProperty
            .create("vertical", Vertical::class.java, UP, SAME, DOWN)
    }

}